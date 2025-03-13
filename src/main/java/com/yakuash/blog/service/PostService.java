package com.yakuash.blog.service;

import java.util.List;
import java.util.Optional;

import com.yakuash.blog.entity.PostEntity;

public interface PostService {
	
	List<PostEntity> getAllPost();
	Optional<PostEntity> getPostById(Long id);
	List<PostEntity> getPostByUserId(Long userId);
	void createdPost(PostEntity post);
	void updatePost(Long id,PostEntity post);
	void deletePostById(Long id);
	List<PostEntity> searchPostByTittle(String tittle);	
}
