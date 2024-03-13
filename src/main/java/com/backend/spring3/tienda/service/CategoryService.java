package com.backend.spring3.tienda.service;

import java.io.IOException;
import java.util.List;

import com.backend.spring3.tienda.dto.CategoryDto;
import org.springframework.web.multipart.MultipartFile;




public interface CategoryService {
    
    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto addCategory(String name, MultipartFile file)throws IOException;


}
