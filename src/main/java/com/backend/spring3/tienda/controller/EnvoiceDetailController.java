package com.backend.spring3.tienda.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.spring3.tienda.dto.EnvoiceDetailDto;
import com.backend.spring3.tienda.service.impl.EnvoiceDetailServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/envoiceDetail")
@AllArgsConstructor
public class EnvoiceDetailController {

    private EnvoiceDetailServiceImpl envoiceDetailServiceImpl;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/envoice/{id}")
    public ResponseEntity<List<EnvoiceDetailDto>> getEnvoiceDetailXIdEnvoice(@PathVariable("id") String idEnvoice) {
        List<EnvoiceDetailDto> books = envoiceDetailServiceImpl.listEnvoiceXIdEnvoice(idEnvoice);
        return ResponseEntity.ok(books);
    }
    
}
