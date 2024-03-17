package com.backend.spring3.tienda.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.spring3.tienda.dto.CategoryDto;
import com.backend.spring3.tienda.service.CategoryService;

import lombok.AllArgsConstructor;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {
    

    private CategoryService categoryService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<CategoryDto> addCategory(@RequestParam("name") String name,@RequestParam("file")MultipartFile file) throws IOException{

        CategoryDto savedCategory = categoryService.addCategory(name, file);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long todoId) {
        CategoryDto categoryDto = categoryService.getCategory(todoId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}
