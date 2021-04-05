package io.educare.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import io.educare.dto.LoginDto;
import io.educare.dto.UserDto;
import io.educare.entity.User;

public interface UserService {
	 public User signup(UserDto userDto);
	 public User login(LoginDto loginDto, HttpServletResponse res);
	 public void logout(HttpServletResponse res);
	 public Optional<User> getUserWithAuthorities(String username);
	 public Optional<User> getMyUserWithAuthorities();
}
