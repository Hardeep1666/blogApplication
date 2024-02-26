package com.blogApp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogApp.dto.PostDto;
import com.blogApp.entity.Category;
import com.blogApp.entity.Post;
import com.blogApp.entity.User;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.payload.PostResponse;
import com.blogApp.repository.CatergoryRepo;
import com.blogApp.repository.PostRepository;
import com.blogApp.repository.UserRepository;
import com.blogApp.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CatergoryRepo catergoryRepo;

	@Autowired
	private PostRepository postRepository;

	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

		Category category = catergoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
		post.setFileName("default.txt");
		post.setCategory(category);
		post.setUser(user);

		Post savedPost = postRepository.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

		post.setAddedDate(new Date());
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setFileName(postDto.getFileName());

		Post updatedpost = postRepository.save(post);
		return modelMapper.map(updatedpost, PostDto.class);
	}

	@Override
	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post ", "postId", postId));
		postRepository.delete(post);

	}

	@Override
	public PostDto getPostById(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post ", "postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepository.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> post = posts.stream().map((e) -> this.modelMapper.map(e, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(post);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = postRepository.findByUser(user);
		return posts.stream().map((e) -> this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByUser(Long userId){
	
	   User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","UserId", userId))	;
	    List<Post> posts= postRepository.findByUser(user);
	      return posts.stream().map((e)-> this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
		
		
	}

	@Override
	public List<PostDto> getPostBycategory(Long categoryId) {
		Category category = catergoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = postRepository.findByCategory(category);
		return posts.stream().map((e) -> this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
	}

}
