package com.backend.spring3.tienda.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backend.spring3.tienda.dto.BookDto;
import com.backend.spring3.tienda.dto.EnvoiceDto;
import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.entity.Envoice;
import com.backend.spring3.tienda.entity.User;
import com.backend.spring3.tienda.exception.ResourceNotFoundException;
import com.backend.spring3.tienda.repository.EnvoiceRepository;
import com.backend.spring3.tienda.repository.UserRepository;
import com.backend.spring3.tienda.service.EnvoiceService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnvoiceServiceImpl implements EnvoiceService{

    private EnvoiceRepository envoiceRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(EnvoiceServiceImpl.class);


    @Override
    public Envoice saveEnvoice(String total,String emailUser) {

       logger.info("Entrando en EnvoiceServiceImpl");

       User user= userRepository.findByEmail(emailUser).orElseThrow(() -> new ResourceNotFoundException("Id Usuario no encontrado con id: "));

        Envoice envoice = new Envoice();

        Integer numberEnvoice = findMaxNumber();

        envoice.setNumber(numberEnvoice);
        envoice.setPublicationDate(LocalDate.now());
        envoice.setTotal(Integer.parseInt(total));
        envoice.setUser(user);

        Envoice envoideSave = envoiceRepository.save(envoice);

        return envoideSave;
    }

    @Override
    public Integer findMaxNumber() {
    
      Integer lastNumber = envoiceRepository.findMaxNumber();

      logger.info("EL NUMERO DA "+lastNumber);

      Integer numberEnvoice=lastNumber == null ? 1 :lastNumber+1;

      return numberEnvoice;
    }

    @Override
    public List<EnvoiceDto> envoiceXIdUser(String id) {
     return envoiceRepository.envoiceXIdUser(id).stream()
                .map(envoice -> modelMapper.map(envoice, EnvoiceDto.class))
                .collect(Collectors.toList());
        
    }

    @Override
    public EnvoiceDto envoiceId(String id) {
      Envoice envoice = envoiceRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id :" + id));

                return modelMapper.map(envoice, EnvoiceDto.class);
    }
    
}
