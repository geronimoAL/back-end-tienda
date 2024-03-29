package com.backend.spring3.tienda.dto;




import java.time.LocalDate;
import java.util.Set;

import com.backend.spring3.tienda.entity.Author;
import com.backend.spring3.tienda.entity.Category;
import com.backend.spring3.tienda.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    
    private String id;
    private String title;
    private String editorial;
    private String publicationDate;
    private String description;
    private Integer amount;
    private Integer inStock;
    private Integer price;
    private String imageUrl;
    private String authorName;
    private Set<Category> categories;
    private String userId;
}

