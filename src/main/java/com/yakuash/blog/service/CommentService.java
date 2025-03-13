package com.yakuash.blog.service;

import java.util.Optional;

import com.yakuash.blog.entity.CommentEntity;

public interface CommentService {
	
	Optional<CommentEntity> getCommentById(Long id);
	void createComment (CommentEntity comment);
	void updtaeComment (Long id, CommentEntity comment);
	void deleteCommentById(Long id);
}
