package com.backend.spring3.tienda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.spring3.tienda.dto.AuthorDto;
import com.backend.spring3.tienda.service.AuthorService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/author")
@AllArgsConstructor
public class AuthorController {
 
   private AuthorService authorService;
    
    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAllTodos() {
        List<AuthorDto> authors = authorService.getAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDto> getTodo(@PathVariable("id") Long authorId) {
        AuthorDto authorDto = authorService.getAuthor(authorId);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }

    
    @PostMapping("/save")
    public ResponseEntity<AuthorDto> addTodo(@RequestBody AuthorDto authorDto){

        AuthorDto savedAuthor = authorService.addAuthor(authorDto);

        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }
}
