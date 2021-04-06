package io.educare.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.LoginDto;
import io.educare.dto.UserDto;
import io.educare.entity.User;
import io.educare.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserServiceImpl userService;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> insertUser(UserDto userDto,
			@RequestParam(value = "file", required = false) MultipartFile mfile) {

		System.out.println(userDto);
		System.out.println("=================");
		System.out.println(userDto.getUsername());
		User inserteduser = null;
		if (mfile != null) {
			inserteduser = userService.insertUser(userDto, mfile);
		} else {
			inserteduser = userService.insertUserNoimg(userDto);
		}
		return new ResponseEntity<User>(inserteduser, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<User> login(@RequestBody LoginDto loginDto, HttpServletResponse res) {
		return new ResponseEntity<User>(userService.login(loginDto, res), HttpStatus.OK);
	}

	@PostMapping("/logout")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public void logout(HttpServletResponse res) {
		userService.logout(res);
	}

	@GetMapping("/myinfo") // 프론트에서 로그인한 유저의 username 보내 조회
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<User> getMyUser(@RequestParam String username) {
		User myUserInfo = userService.getMyUser(username);
		return new ResponseEntity<User>(myUserInfo, HttpStatus.OK);
	}

	@GetMapping("/{username}") // 강사가 학생들 조회 @PathVariable 방식
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		User userInfo = userService.getUser(username);
		return new ResponseEntity<User>(userInfo, HttpStatus.OK);
	}

	@GetMapping("/allusers") // 강사가 전체 학생 리스트 조회 가능
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<User>> getUserList(@RequestParam String role) {
		List<User> usersInfo = userService.getUserList(role);
		return new ResponseEntity<List<User>>(usersInfo, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(UserDto userDto, @RequestParam(value = "file", required = false) MultipartFile mfile) {
		User updatedUser = null;
		if (mfile != null) {
			updatedUser = userService.updateUser(userDto, mfile);
		} else {
			updatedUser = userService.updateUserNoimg(userDto);
		}
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<HttpStatus> deleteUser(@RequestParam String username, HttpServletResponse res) {
		userService.deleteUser(username, res);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}