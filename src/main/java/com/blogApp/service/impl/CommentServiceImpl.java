package com.blogApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.dto.CommentDto;
import com.blogApp.entity.Comment;
import com.blogApp.entity.Post;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.repository.CommentRepo;
import com.blogApp.repository.PostRepository;
import com.blogApp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment SavedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(SavedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commnetId) {
		Comment comment = this.commentRepo.findById(commnetId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commnetId));
		this.commentRepo.delete(comment);
	}

}
