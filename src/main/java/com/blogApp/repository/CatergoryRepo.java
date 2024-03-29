package com.blogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogApp.entity.Category;

@Repository
public interface CatergoryRepo extends JpaRepository<Category, Long>{

}
