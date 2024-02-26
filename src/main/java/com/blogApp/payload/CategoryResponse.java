package com.blogApp.payload;

import java.util.List;

import com.blogApp.dto.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

	private List<CategoryDto> content ;
	private int pageNumber;
	private int pageSize;
	private long totalElements ;
	private int totalPages;
	private boolean lastPage;
}
