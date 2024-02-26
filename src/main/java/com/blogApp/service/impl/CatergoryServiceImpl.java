package com.blogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogApp.dto.CategoryDto;
import com.blogApp.entity.Category;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.CategoryResponse;
import com.blogApp.repository.CatergoryRepo;
import com.blogApp.service.CategoryService;

@Service
public class CatergoryServiceImpl implements CategoryService {

	ModelMapper modelmapper = new ModelMapper();
	@Autowired
	private CatergoryRepo categoryRepo;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = modelmapper.map(categoryDto, Category.class);
		Category saved = categoryRepo.save(category);
		CategoryDto savedDto = modelmapper.map(saved, CategoryDto.class);
		return savedDto;

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
		Category category = this.categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not found", "id", id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		Category savedcategory = this.categoryRepo.save(category);

		return this.modelmapper.map(savedcategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long id) {
		Category category = this.categoryRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryResponse getAllCategory(int pageNumber, int pageSize) {
	
		Pageable p= PageRequest.of(pageNumber, pageSize);
		 Page<Category> Pagecategories = this.categoryRepo.findAll(p);
		 List<Category> categories=Pagecategories.getContent();
         List<CategoryDto> category= categories.stream().map((e) -> modelmapper.map(e, CategoryDto.class)).collect(Collectors.toList()); 
		 
         CategoryResponse categoryResponse= new CategoryResponse();
		 categoryResponse.setContent(category);
		 categoryResponse.setLastPage(Pagecategories.isLast());
		 categoryResponse.setPageNumber(Pagecategories.getNumber());
		 categoryResponse.setPageSize(Pagecategories.getSize());
		 categoryResponse.setTotalElements(Pagecategories.getTotalElements());
		 categoryResponse.setTotalPages(Pagecategories.getTotalPages());
		 
		 
		return categoryResponse;
	}

	@Override
	public CategoryDto getCatergoryById(Long id) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
		return modelmapper.map(category, CategoryDto.class);
	}

}
