package com.yakuash.blog.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yakuash.blog.entity.PostEntity;
import com.yakuash.blog.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImple implements PostService{
	
	private final PostRepository postRepository;
	
	@Override
	public List<PostEntity> getAllPost() {
		return postRepository.findAll();
	}

	@Override
	public Optional<PostEntity> getPostById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public List<PostEntity> getPostByUserId(Long userId) {
		return postRepository.findByUserId(userId);
	}

	@Override
	public void createdPost(PostEntity post) {
		postRepository.save(post);
	}

	@Override
	public void updatePost(Long id, PostEntity post) {
		PostEntity postEnty = getPostById(id).orElseThrow(() -> new InvalidParameterException("invalid post id"));
		postEnty.setTittle(post.getTittle());
		postEnty.setContent(post.getContent());
		postRepository.save(post);
	}

	@Override
	public void deletePostById(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<PostEntity> searchPostByTittle(String tittle) {
		return postRepository.findByTittleContainingIgnoreCase(tittle);
	}
}
