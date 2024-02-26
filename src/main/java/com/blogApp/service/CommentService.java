package com.blogApp.service;

import org.springframework.stereotype.Service;

import com.blogApp.dto.CommentDto;

@Service
public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Long postId);
	
	void deleteComment(Long commnetId);
}
