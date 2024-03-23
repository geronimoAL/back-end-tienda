package com.backend.spring3.tienda.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.BookDto;

public interface BookService {
    
    BookDto addBook(String emailUser,String title,String editorial, String description,String date,String amount, String price,String authorId, String categorias,MultipartFile file)throws IOException;

    BookDto getBook(String id);

    List<BookDto> getAllBooks();

    BookDto updateBook(String id,MultipartFile file,String title,String editorial, String description,String date,String amount, String price,String authorId, String categorias)throws IOException;

    void deleteBook(String id)throws IOException;

    List<BookDto> searchBookXCategoryId(String idCategory);

    List<BookDto> searchBookXNameOrEditorial(String name);

    List<BookDto> getBookLimit();

    List<BookDto> getBookIdUser(String idUser);

    
}
