package com.backend.spring3.tienda.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spring3.tienda.dto.BookDto;
import com.backend.spring3.tienda.dto.CategoryDto;
import com.backend.spring3.tienda.dto.EnvoiceDto;
import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.entity.Envoice;
import com.backend.spring3.tienda.entity.EnvoiceDetail;
import com.backend.spring3.tienda.repository.EnvoiceDetailRepository;
import com.backend.spring3.tienda.repository.EnvoiceRepository;
import com.backend.spring3.tienda.service.EnvoiceService;
import com.backend.spring3.tienda.service.impl.EnvoiceDetailServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/save")
    public ResponseEntity<EnvoiceDto> addEnvoice( @RequestParam("cart") String cart, @RequestParam("total") String total) throws JsonMappingException, JsonProcessingException{

    logger.info("Entrando en EnvoiceContoller ");

    EnvoiceDto envoiceDto=envoiceDetailServiceImpl.saveEnvoiceDetail(cart, total);


    return new ResponseEntity<>(envoiceDto ,HttpStatus.CREATED);
    }
    
}
