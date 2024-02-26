package com.blogApp.service;

import org.springframework.stereotype.Service;

import com.blogApp.dto.CategoryDto;
import com.blogApp.payload.CategoryResponse;

@Service
public interface CategoryService {
	//create category
    CategoryDto createCategory(CategoryDto categoryDto);
    
    CategoryDto updateCategory(CategoryDto categoryDto,Long id);
    
    void deleteCategory(Long id);
    
    CategoryResponse getAllCategory(int pageNumber, int pageSize);
    
    CategoryDto getCatergoryById(Long id);
}
