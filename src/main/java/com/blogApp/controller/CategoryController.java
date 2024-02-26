package com.blogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.dto.CategoryDto;
import com.blogApp.payload.ApiResponse;
import com.blogApp.payload.CategoryResponse;
import com.blogApp.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
    private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto category=  this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(category, HttpStatus.CREATED );
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Long categoryId)
	{
		CategoryDto updatedcat= this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedcat);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> DeleteCategory(@PathVariable("categoryId") Long categoryId) {
		this.categoryService.deleteCategory(categoryId);
		ResponseEntity<?> response= new ResponseEntity<>(new ApiResponse("Category Deleted Succsfully",true), HttpStatus.OK);
		return response ;
	}
	
	@GetMapping("/")
	public ResponseEntity<CategoryResponse> getAllcategory(@RequestParam(value="pageNumber", defaultValue="0",required= false)Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue="0",required= false) Integer PageSize){
		ResponseEntity< CategoryResponse> reponseEntity = ResponseEntity.ok(this.categoryService.getAllCategory( pageNumber,  PageSize)) ;
		
		return reponseEntity;
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long  categoryId)
	{
		CategoryDto get= this.categoryService.getCatergoryById(categoryId);
		return ResponseEntity.ok(get);
	}
}
