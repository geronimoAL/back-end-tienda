package com.backend.spring3.tienda.service;

import java.util.List;

import com.backend.spring3.tienda.dto.AuthorDto;


public interface AuthorService {
    

    AuthorDto getAuthor(Long id);

    List<AuthorDto> getAuthors();

    AuthorDto addAuthor(AuthorDto authorDto);
    
}
