package com.backend.spring3.tienda.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.BookDto;

public interface BookService {
    
    BookDto addBook(String title,String editorial, String description,String date,String amount, String price,String authorId, String categorias,MultipartFile file)throws IOException;

    BookDto getTodo(String id);

    List<BookDto> getAllTodos();

    BookDto updateTodo(BookDto libroDto, Long id,MultipartFile file)throws IOException;

    void deleteTodo(Long id)throws IOException;

    List<BookDto> searchBookXCategoryId(String idCategory);

    List<BookDto> searchBookXNameOrEditorial(String name);


}
