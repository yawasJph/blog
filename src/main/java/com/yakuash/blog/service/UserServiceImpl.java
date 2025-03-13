package com.yakuash.blog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yakuash.blog.entity.UserEntity;
import com.yakuash.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public void createUser(UserEntity user) {
		userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> getUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<UserEntity> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
