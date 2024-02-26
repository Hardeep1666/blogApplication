package com.blogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.dto.UserDto;
import com.blogApp.payload.ApiResponse;
import com.blogApp.payload.UserResponse;
import com.blogApp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<UserResponse>   getAllUser(@RequestParam(value="pageNumber", defaultValue="0",required= false)Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue="0",required= false)Integer pageSize) {
		UserResponse userResponse = this.userService.getAllUser(pageNumber,pageSize);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto) {
		UserDto user = this.userService.createUser(userdto);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getuserbyId(@PathVariable("userId") Long id) {
		UserDto userdto = this.userService.getUserbyId(id);
		return ResponseEntity.ok(userdto);
	}

	@PostMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid  @RequestBody UserDto userdto, @PathVariable("userId") Long id) {
		UserDto updateduser = this.userService.updateUser(userdto, id);
		return ResponseEntity.ok(updateduser);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Long id) {
		this.userService.deleteUser(id);
		ResponseEntity<?> responseEntity = new ResponseEntity<>(new ApiResponse("User deleted succesfully", true),HttpStatus.OK );
		return responseEntity;
	}

}
