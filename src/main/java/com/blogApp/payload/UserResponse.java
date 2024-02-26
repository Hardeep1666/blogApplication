package com.blogApp.payload;

import java.util.List;

import com.blogApp.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private List<UserDto> content ;
	private int pageNumber;
	private int pageSize;
	private long totalElements ;
	private int totalPages;
	private boolean lastPage;
}
