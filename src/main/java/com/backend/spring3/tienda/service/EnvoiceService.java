package com.backend.spring3.tienda.service;

import java.util.List;

import com.backend.spring3.tienda.dto.EnvoiceDto;
import com.backend.spring3.tienda.entity.Envoice;

public interface EnvoiceService {
    
    Envoice saveEnvoice(String total,String idUser);

    Integer findMaxNumber();

    List<EnvoiceDto> envoiceXIdUser(String id);

    EnvoiceDto envoiceId(String id);
}
