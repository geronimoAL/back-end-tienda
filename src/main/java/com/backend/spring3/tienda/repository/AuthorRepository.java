package com.backend.spring3.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.spring3.tienda.entity.Author;


public interface AuthorRepository extends JpaRepository<Author, String>{
    
}
