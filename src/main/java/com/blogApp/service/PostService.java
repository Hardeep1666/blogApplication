package com.blogApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogApp.dto.PostDto;
import com.blogApp.payload.PostResponse;

@Service
public interface PostService {

	/// create

	PostDto createPost(PostDto postDto, Long userId, Long categoryId);

	PostDto updatePost(PostDto postDto, Long postId);

	void deletePost(Long postId);

	PostDto getPostById(Long postId);

	PostResponse getAllPost(int pageNumber ,int pageSize, String sortBy, String sortDir);

	List<PostDto> getPostsByUser(Long userId);
	
	
	List<PostDto> getPostByUser(Long userId);

	List<PostDto> getPostBycategory(Long categoryId);

}
