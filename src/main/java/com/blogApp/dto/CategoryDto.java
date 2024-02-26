package com.blogApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Long categoryId;
	@NotBlank
	private String categoryTitle;
	
	@NotBlank
	@Size(min= 5)
	private String categoryDescription;
}
