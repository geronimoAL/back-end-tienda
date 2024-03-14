package com.backend.spring3.tienda.service;

import com.backend.spring3.tienda.entity.Envoice;

public interface EnvoiceService {
    
    Envoice saveEnvoice(String total);

    Integer findMaxNumber();
}
