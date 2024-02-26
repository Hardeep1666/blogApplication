package com.blogApp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.dto.PostDto;
import com.blogApp.payload.ApiResponse;
import com.blogApp.payload.PostResponse;
import com.blogApp.service.FileService;
import com.blogApp.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String imagepath;
	
	@Value("${project.files}")
	private String filepath;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable("userId") Long userId,
			@PathVariable("categoryId") Long categoryId) {

		PostDto createdPost= postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	
	@PostMapping("post/image/upload/{postId}/")
	ResponseEntity<PostDto> UploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Long postId) throws IOException {

		PostDto postDto= this.postService.getPostById(postId);
		String fileName= this.fileService.uploadImage(imagepath, image);
		postDto.setImageName(fileName);
		PostDto updatedPost= this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	@GetMapping(value="/post/image/{postId}/",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable Long postId, HttpServletResponse response) throws IOException
	{
		PostDto post= this.postService.getPostById(postId);
		String imageName= post.getImageName();
		
		InputStream resource= this.fileService.getResource(imagepath, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	@PostMapping("post/file/upload/{postId}/")
	ResponseEntity<PostDto> UploadFile(@RequestParam("file") MultipartFile file,
			@PathVariable("postId") Long postId) throws IOException {

		PostDto postDto= this.postService.getPostById(postId);
		String fileName= this.fileService.uploadFiles(filepath, file);
		postDto.setFileName(fileName);
		PostDto updatedPost= this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	
	@GetMapping(value="/post/file/{postId}/",produces = MediaType.APPLICATION_PDF_VALUE)
	public void downloadFile(@PathVariable Long postId, HttpServletResponse response) throws IOException
	{
		PostDto post= this.postService.getPostById(postId);
		String fileName= post.getFileName();
		
		InputStream resource= this.fileService.getResource(filepath, fileName);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
	@PostMapping ("/post/{postId}/")
	ResponseEntity<PostDto> UpdatePost(@RequestBody PostDto postDto,
			@PathVariable("postId") Long postId) {

		PostDto updatedPost= postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/post/")
	ResponseEntity<List<PostDto>> GetPostByUser(@PathVariable("userId") Long userId)
	{
		List<PostDto> posts= postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/post/")
	ResponseEntity<List<PostDto>> GetPostByCategory(@PathVariable("categoryId") Long categoryId)
	{
		List<PostDto> posts= postService.getPostBycategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/")
	ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber", defaultValue="0",required= false)Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue="5",required= false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue="postId", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required= false)String sortDir){
		
		PostResponse posts= this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir );
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@GetMapping("posts/{postId}/")
	ResponseEntity<PostDto> getPostById(@PathVariable Long postId)
	{
		PostDto post= this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@DeleteMapping("posts/{postId}/")
	ResponseEntity<?> deletePost(@PathVariable Long postId )
	{
	postService.deletePost(postId);
	ResponseEntity<?> responseEntity = new ResponseEntity<>(new ApiResponse("Post deleted succesfully", true),HttpStatus.OK );
	return responseEntity;
	}
}

