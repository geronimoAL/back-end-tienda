package com.backend.spring3.tienda.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnvoiceDto {

    private String id;

    private Integer number;

    private LocalDate publicationDate;

    private Integer total;
}
