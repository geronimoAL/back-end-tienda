package com.backend.spring3.tienda.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.backend.spring3.tienda.dto.EnvoiceDetailDto;
import com.backend.spring3.tienda.dto.EnvoiceDto;
import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.entity.Envoice;
import com.backend.spring3.tienda.entity.EnvoiceDetail;
import com.backend.spring3.tienda.exception.TodoAPIException;
import com.backend.spring3.tienda.repository.BookRepository;
import com.backend.spring3.tienda.repository.EnvoiceDetailRepository;
import com.backend.spring3.tienda.service.EnvoiceDetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class EnvoiceDetailServiceImpl implements EnvoiceDetailService {

    private static final Logger logger = LoggerFactory.getLogger(EnvoiceDetailServiceImpl.class);

    private EnvoiceServiceImpl envoiceServiceImpl;

    private EnvoiceDetailRepository envoiceDetailRepository;

     private ModelMapper modelMapper;

    private ObjectMapper objectMapper;

    private BookRepository bookRepository;

       // List<Book>listaBookForId=Arrays.stream(booksArray)
        //                     .map(book -> bookRepository.findById(book.getId()).get())
        //                     .collect(Collectors.toList());

    @Override
    public EnvoiceDto saveEnvoiceDetail(String cart, String total,String emailUser) throws JsonMappingException, JsonProcessingException {

        logger.info("Entrando en EnvoiceDetailServiceImpl");

        verifyContainIsNull(cart,total);
        verifyContain(total);

        Book[] booksArray = objectMapper.readValue(cart, Book[].class);

        Envoice envoiceSaved = envoiceServiceImpl.saveEnvoice(total,emailUser);

        List<EnvoiceDetail> booksElegidos = Arrays.stream(booksArray)
                .map(book -> new EnvoiceDetail(
                        null,
                        book.getTitle(),
                        Integer.parseInt(book.getPrice()),
                        book.getAmount(),
                        Integer.parseInt(book.getPrice()) * book.getAmount(),
                        book,
                        envoiceSaved))
                .collect(Collectors.toList());
        
        envoiceDetailRepository.saveAll(booksElegidos);


        for (Book book : booksArray) {
            Book bookToUpdate = bookRepository.findById(book.getId()).get();
            if (bookToUpdate != null) {
                int updatedStock = bookToUpdate.getInStock() - book.getAmount();
                bookToUpdate.setInStock(updatedStock);
                bookRepository.save(bookToUpdate);
            }
        }

        EnvoiceDto savedEnvoiceDto = modelMapper.map(envoiceSaved, EnvoiceDto.class);

        return savedEnvoiceDto;


    }

    private void verifyContain(String total){

        String regex = "[a-zA-Z]+";

        boolean contains = Pattern.matches(regex, total);

        if (contains) {
            throw new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "No puede contener letras el total");
        }

        
    }

    private void verifyContainIsNull(String cart,String total){

        if (cart==null) {
            throw new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "No puede ser nulo");
        }

        if (total==null) {
            throw new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "No puede ser nulo");
        }
        
    }

    @Override
    public List<EnvoiceDetailDto> listEnvoiceXIdEnvoice(String id) {
        return envoiceDetailRepository.envoiceDetailXIdEnvoice(id).stream()
                                .limit(3)
                                .map(envoiceDetail -> modelMapper.map(envoiceDetail, EnvoiceDetailDto.class))
                                .collect(Collectors.toList());
    }

}
