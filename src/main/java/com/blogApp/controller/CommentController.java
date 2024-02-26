package com.blogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.dto.CommentDto;
import com.blogApp.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{PostId}/comments")
	ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commetDto,@PathVariable("PostId") Long PostId)
	{
		CommentDto Savedcomment= this.commentService.createComment(commetDto, PostId);
           return new ResponseEntity<CommentDto>(Savedcomment, HttpStatus.CREATED);		
	}
	
}
