package com.backend.spring3.tienda.dto;



import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}

