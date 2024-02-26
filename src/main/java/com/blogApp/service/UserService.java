package com.blogApp.service;

import org.springframework.stereotype.Service;

import com.blogApp.dto.UserDto;
import com.blogApp.payload.UserResponse;

@Service
public interface UserService {

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Long id);

	UserResponse getAllUser(int pageNumber ,int pageSize);

	UserDto getUserbyId(Long userId);

	void deleteUser(Long userId);
}
