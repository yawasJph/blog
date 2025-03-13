package com.yakuash.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yakuash.blog.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{
	
	 List<PostEntity> findByUserId(Long userId); 
	 List<PostEntity> findByTittleContainingIgnoreCase(String tittle); 	
}
