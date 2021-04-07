package io.educare.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.LoginDto;
import io.educare.dto.UserDto;
import io.educare.entity.User;
import io.educare.exception.UserNotFoundException;

public interface UserService {
	public User insertUser(UserDto userDto, MultipartFile mfile);

	public User insertUserNoimg(UserDto userDto);

	public User login(LoginDto loginDto, HttpServletResponse res);

	public void logout(HttpServletResponse res);
	
	public User getMyUser(String username);
	
	public User getUser(String username);
	
	public List<User> getUserList(String role);
	
	public User updateUser(UserDto userDto, MultipartFile mfile);
	
	public User updateUserNoimg(UserDto userDto);
	
	public void deleteUser(String username, HttpServletResponse res);
}
