package com.backend.spring3.tienda.controller;

import java.io.IOException;

import java.util.*;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.BookDto;

import com.backend.spring3.tienda.service.BookService;
import java.awt.image.BufferedImage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("api/book")
@AllArgsConstructor
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/save")
    public ResponseEntity<BookDto> addTodo(
            @RequestParam("title") String title,
            @RequestParam("editorial") String editorial,
            @RequestParam("description") String description,
            @RequestParam("fecha") String date,
            @RequestParam("unidades") String amount,
            @RequestParam("precio") String price,
            @RequestParam("author") String authorId,
            @RequestParam("categorias") String categories,
            @RequestParam("file") MultipartFile file) throws IOException {
        
        //BufferedImage bi = ImageIO.read(file.getInputStream());
		// if (bi == null) {
        //     throw new TodoAPIException(HttpStatus.BAD_REQUEST, "File is requerided!");
		// }
        
        logger.info("Entrando en libroContoller");

        BookDto savedTodo = bookService.addBook(title,editorial,description,date,amount,price,authorId,categories,file);


        return new ResponseEntity<>(savedTodo ,HttpStatus.CREATED);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") String todoId) {
        BookDto libroDto = bookService.getBook(todoId);
        return new ResponseEntity<>(libroDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> todos = bookService.getAllBooks();
        return ResponseEntity.ok(todos);
    }

    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateTodo(
            @RequestParam("title") String title,
            @RequestParam("editorial") String editorial,
            @RequestParam("description") String description,
            @RequestParam("fecha") String date,
            @RequestParam("unidades") String amount,
            @RequestParam("precio") String price,
            @RequestParam("author") String authorId,
            @RequestParam("categorias") String categories,
            @RequestParam(name = "file",required = false) MultipartFile file,
            @PathVariable("id") String bookId) throws IOException {

        logger.info("Entrando a updateBookController ");

        BookDto updatedBookDto = bookService.updateBook(bookId, file,title,editorial, description,date,amount, price,authorId,categories);
      
        return ResponseEntity.ok(updatedBookDto);

    }

   
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") String bookId) throws IOException {
        logger.info("Entrando a deleteBookController ");
        bookService.deleteBook(bookId);
        return ResponseEntity.ok("Todo deleted successfully!.");

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/search/categoryBook/{id}")
    public ResponseEntity<List<BookDto>> getBooksXCategory(@PathVariable("id") String categoryId) {
        List<BookDto> books = bookService.searchBookXCategoryId(categoryId);
        return ResponseEntity.ok(books);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/search/name/{id}")
    public ResponseEntity<List<BookDto>> getBooksXNameBookOrEditorial(@PathVariable("id") String categoryId) {
        List<BookDto> books = bookService.searchBookXNameOrEditorial(categoryId);
        return ResponseEntity.ok(books);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/limit")
    public ResponseEntity<List<BookDto>> getAllBooksLimit() {
        List<BookDto> todos = bookService.getBookLimit();
        return ResponseEntity.ok(todos);
    }

}
