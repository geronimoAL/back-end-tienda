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

import com.backend.spring3.tienda.dto.EnvoiceDto;
import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.entity.Envoice;
import com.backend.spring3.tienda.entity.EnvoiceDetail;
import com.backend.spring3.tienda.exception.TodoAPIException;
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

    @Override
    public EnvoiceDto saveEnvoiceDetail(String cart, String total) throws JsonMappingException, JsonProcessingException {

        logger.info("Entrando en EnvoiceDetailServiceImpl");

        verifyContainIsNull(cart,total);
        verifyContain(total);

        Book[] booksArray = objectMapper.readValue(cart, Book[].class);

        Envoice envoiceSaved = envoiceServiceImpl.saveEnvoice(total);

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

        booksElegidos.forEach(envoicee ->

        envoiceDetailRepository.save(envoicee)

        );

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

    private void verifyContainIsNull(String thing,String thing2){


        if (thing==null) {
            throw new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "No puede ser nulo");
        }

        if (thing2==null) {
            throw new TodoAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "No puede ser nulo");
        }

        
    }

}
