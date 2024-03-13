package com.backend.spring3.tienda.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.spring3.tienda.dto.AuthorDto;
import com.backend.spring3.tienda.entity.Author;
import com.backend.spring3.tienda.exception.ResourceNotFoundException;
import com.backend.spring3.tienda.repository.AuthorRepository;
import com.backend.spring3.tienda.service.AuthorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;

    private ModelMapper modelMapper;

    @Override
    public AuthorDto getAuthor(Long id) {
           Author author = authorRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id:" + id));

        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public List<AuthorDto> getAuthors() {
       List<Author> authors = authorRepository.findAll();

        return authors.stream().map((author) -> modelMapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
          Author author = modelMapper.map(authorDto, Author.class);

          Author savedAuthor = authorRepository.save(author);
  
  
          AuthorDto savedAuthorDto = modelMapper.map(savedAuthor, AuthorDto.class);
  
          return savedAuthorDto;
    }
    
}
