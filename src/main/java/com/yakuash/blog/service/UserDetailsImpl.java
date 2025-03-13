package com.yakuash.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yakuash.blog.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@Service
public class UserDetailsImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	HttpSession session;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserEntity> optionalUser = userService.getUserByUsername(username);
		
		if(optionalUser.isPresent()) {
			session.setAttribute("user_session_id", optionalUser.get().getId());
			UserEntity user = optionalUser.get();
			return User
					.builder()
					.username(user.getUsername())
					.password(user.getPassword())
					.roles()
					.build();
		}else {
			throw new UsernameNotFoundException("user not found");
		}
	}
}
