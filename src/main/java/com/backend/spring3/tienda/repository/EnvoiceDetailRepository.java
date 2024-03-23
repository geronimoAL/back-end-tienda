package com.backend.spring3.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.spring3.tienda.entity.EnvoiceDetail;



public interface EnvoiceDetailRepository extends JpaRepository<EnvoiceDetail,String>{
    
     @Query("SELECT F FROM EnvoiceDetail F JOIN F.envoice C WHERE C.id = :id")
    public List<EnvoiceDetail> envoiceDetailXIdEnvoice(@Param("id") String idEnvoice);


}