package com.blogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogApp.dto.UserDto;
import com.blogApp.entity.User;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.UserResponse;
import com.blogApp.repository.UserRepository;
import com.blogApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userdto) {
		User user= this.dtoToUser(userdto);
		User savedUser= userRepository.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long id) {
		User user = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", id));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getPassword());
		user.setAddress(userDto.getAddress());

		User updatedUser = this.userRepository.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserResponse getAllUser(int pageNumber ,int pageSize) {
		
	Pageable p= PageRequest.of(pageNumber, pageSize);
	   Page<User> pageUser=  this.userRepository.findAll(p);
	   List<User> users= pageUser.getContent();
	   List<UserDto> user= users.stream().map((e)-> this.modelMapper.map(e, UserDto.class)).collect(Collectors.toList());
		
	   UserResponse usersResponse= new UserResponse();
	   usersResponse.setContent(user);
	   usersResponse.setLastPage(pageUser.isLast());
	   usersResponse.setPageNumber(pageUser.getNumber());
	   usersResponse.setPageSize(pageUser.getSize());
	   usersResponse.setTotalElements(pageUser.getTotalElements());
	   usersResponse.setTotalPages(pageUser.getTotalPages());
	   return usersResponse;
	}

	@Override
	public UserDto getUserbyId(Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
		return this.userToDto(user);
	}

	@Override
	public void deleteUser(Long userId) {
	User user=	userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));
      userRepository.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setAddress(userDto.getAddress());

		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = new UserDto();

		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		userDto.setAddress(user.getAddress());

		return userDto;
	}

}
