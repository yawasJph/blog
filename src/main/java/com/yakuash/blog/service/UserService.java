package com.yakuash.blog.service;

import java.util.Optional;

import com.yakuash.blog.entity.UserEntity;

public interface UserService {
	
	void createUser(UserEntity user);
	Optional<UserEntity> getUserById(Long id);
	Optional<UserEntity> getUserByUsername(String username);
}
