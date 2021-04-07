package io.educare.service;

import org.springframework.security.core.userdetails.UserDetails;

import io.educare.entity.User;

public interface CustomUserDetailsService {

	public UserDetails loadUserByUsername(final String username);
	public CustomUserDetails createUser(User user);
	
}
