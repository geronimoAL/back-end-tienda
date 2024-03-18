package com.backend.spring3.tienda.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spring3.tienda.dto.EnvoiceDto;

import com.backend.spring3.tienda.service.impl.EnvoiceDetailServiceImpl;
import com.backend.spring3.tienda.service.impl.EnvoiceServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/envoice")
@AllArgsConstructor
public class EnvoiceController {

    
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);


    private EnvoiceDetailServiceImpl envoiceDetailServiceImpl;

    private EnvoiceServiceImpl envoiceServiceImpl;


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/save")
    public ResponseEntity<EnvoiceDto> addEnvoice( @RequestParam("cart") String cart, @RequestParam("total") String total,@RequestParam("emailUser") String emailUser) throws JsonMappingException, JsonProcessingException{

    logger.info("Entrando en EnvoiceContoller ");

    EnvoiceDto envoiceDto=envoiceDetailServiceImpl.saveEnvoiceDetail(cart, total,emailUser);
    return new ResponseEntity<>(envoiceDto ,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<EnvoiceDto>> getEnvoiceIdUser(@PathVariable("id") String id) {
        logger.info("entrando a EnvoiceController por id user");
        List<EnvoiceDto> envoices = envoiceServiceImpl.envoiceXIdUser(id);
        logger.info("ento me da "+envoices);
        return ResponseEntity.ok(envoices);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<EnvoiceDto> getEnvoiceId(@PathVariable("id") String id) {
        logger.info("entrando a EnvoiceController por id factura");
        EnvoiceDto envoice = envoiceServiceImpl.envoiceId(id);
        return ResponseEntity.ok(envoice);
    }
    
}
