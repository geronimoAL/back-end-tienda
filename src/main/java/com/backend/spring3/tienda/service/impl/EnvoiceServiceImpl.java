package com.backend.spring3.tienda.service.impl;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.backend.spring3.tienda.entity.Envoice;
import com.backend.spring3.tienda.repository.EnvoiceRepository;
import com.backend.spring3.tienda.service.EnvoiceService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EnvoiceServiceImpl implements EnvoiceService{

    private EnvoiceRepository envoiceRepository;

     private static final Logger logger = LoggerFactory.getLogger(EnvoiceServiceImpl.class);


    @Override
    public Envoice saveEnvoice(String total) {

       logger.info("Entrando en EnvoiceServiceImpl");

        Envoice envoice = new Envoice();

        Integer numberEnvoice = findMaxNumber();

        envoice.setNumber(numberEnvoice);
        envoice.setPublicationDate(LocalDate.now());
        envoice.setTotal(Integer.parseInt(total));

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
    
}
