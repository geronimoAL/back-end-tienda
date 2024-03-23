package com.backend.spring3.tienda.service;

import java.util.List;

import com.backend.spring3.tienda.dto.EnvoiceDetailDto;
import com.backend.spring3.tienda.dto.EnvoiceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface EnvoiceDetailService {
    
    EnvoiceDto saveEnvoiceDetail(String cart,String total,String id)throws JsonMappingException, JsonProcessingException;

    List<EnvoiceDetailDto> listEnvoiceXIdEnvoice(String id);


}
