package com.blogApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {

	@NotBlank
	private String name;
	@Email
	private String email;
	
	@NotEmpty
	private String password;
	@NotEmpty
	private String address;
	@NotEmpty
	private String about;

}
