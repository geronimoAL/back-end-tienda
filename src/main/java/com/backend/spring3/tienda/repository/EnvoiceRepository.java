package com.backend.spring3.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.spring3.tienda.entity.Envoice;


public interface EnvoiceRepository extends JpaRepository<Envoice, String>{
    
     @Query("SELECT MAX(e.number) FROM Envoice e")
    Integer findMaxNumber();

     @Query("SELECT F FROM Envoice F JOIN F.user C WHERE C.id = :id")
    List<Envoice>envoiceXIdUser(@Param("id") String id);
}
