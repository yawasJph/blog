package com.yakuash.blog.service;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yakuash.blog.entity.CommentEntity;
import com.yakuash.blog.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	
	private final CommentRepository commentRepository;
	
	@Override
	public Optional<CommentEntity> getCommentById(Long id) {
		return commentRepository.findById(id);
	}

	@Override
	public void createComment(CommentEntity comment) {
		commentRepository.save(comment);
	}

	@Override
	public void updtaeComment(Long id, CommentEntity comment) {
		CommentEntity commentEnty = commentRepository.findById(id).orElseThrow(() -> new InvalidParameterException("invalid comment id"));
		commentEnty.setContent(comment.getContent());
		commentRepository.save(commentEnty);
	}

	@Override
	public void deleteCommentById(Long id) {
		commentRepository.deleteById(id);
	}

}
