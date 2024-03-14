package com.backend.spring3.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.spring3.tienda.entity.Book;


public interface BookRepository extends JpaRepository<Book, String>{
    
    @Query("SELECT F From Book F WHERE F.title LIKE %:name% OR F.editorial LIKE %:name%")
    public List<Book>searchBookXNameOrEditorial(@Param("name") String nameCategory);

    
    @Query("SELECT DISTINCT F FROM Book F JOIN F.categories C WHERE C.id = :category")
    public List<Book> searchBookXCategoryID(@Param("category") String nameCategory);



}
