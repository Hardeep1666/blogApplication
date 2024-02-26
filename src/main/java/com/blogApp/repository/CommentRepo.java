package com.blogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogApp.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
