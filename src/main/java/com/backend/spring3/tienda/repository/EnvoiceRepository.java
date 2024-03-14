package com.backend.spring3.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.spring3.tienda.entity.Envoice;


public interface EnvoiceRepository extends JpaRepository<Envoice, String>{
    
     @Query("SELECT MAX(e.number) FROM Envoice e")
    Integer findMaxNumber();
}
