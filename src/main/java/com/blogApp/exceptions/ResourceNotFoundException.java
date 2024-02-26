package com.blogApp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourcename;
	private String fieldName;
	private long fieldValue;

	public ResourceNotFoundException(String resourcename, String fieldName, long fieldValue) {
		super(String.format("%s Not found with %s: %s", resourcename, fieldName, fieldValue));
		this.resourcename = resourcename;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
