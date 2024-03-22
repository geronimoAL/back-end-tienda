package com.backend.spring3.tienda.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import com.backend.spring3.tienda.dto.CategoryDto;
import com.backend.spring3.tienda.entity.Category;
import com.backend.spring3.tienda.exception.ResourceNotFoundException;
import com.backend.spring3.tienda.repository.CategoryRepository;
import com.backend.spring3.tienda.service.CategoryService;
import com.backend.spring3.tienda.service.CloudinaryService;

import org.springframework.web.multipart.MultipartFile;



import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

     private ModelMapper modelMapper;

    private CloudinaryService cloudinaryService;

    @Override
    public CategoryDto getCategory(Long id) {
      Category category = categoryRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id:" + id));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
       List<Category> categories = categoryRepository.findAll();

        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto addCategory(String name,MultipartFile file)throws IOException {

          Map result = cloudinaryService.upload(file);

          Category category=Category.builder()
          .name(name)
          .imageUrl((String) result.get("url"))
          .cloudinaryId((String) result.get("public_id"))
          .build();


          Category savedCategory = categoryRepository.save(category);
  
  
          CategoryDto savedCategoryDto = modelMapper.map(savedCategory, CategoryDto.class);
  
          return savedCategoryDto;
    }

     
    
}
