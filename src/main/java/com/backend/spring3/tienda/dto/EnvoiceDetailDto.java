package com.backend.spring3.tienda.dto;



import com.backend.spring3.tienda.entity.Book;
import com.backend.spring3.tienda.entity.Envoice;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnvoiceDetailDto {

    private String name;

    private Integer price;

    private Integer amount;

    private Integer total;

    
}
