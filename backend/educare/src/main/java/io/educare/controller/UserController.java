package io.educare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import io.educare.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Boolean> insertUser(UserDto userDto,
			@RequestParam(value = "file", required = false) MultipartFile mfile) {

		Boolean check = null;
		if (mfile != null) {
			check = userService.insertUser(userDto, mfile);
		} else {
			check = userService.insertUserNoimg(userDto);
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto, HttpServletResponse res) {
		return userService.login(loginDto, res);
	}
	
	@PostMapping("/logout")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<Boolean> logout(HttpServletRequest req) {
		return new ResponseEntity<Boolean>(userService.logout(req), HttpStatus.NO_CONTENT);
	}

	@GetMapping("/myinfo") // 프론트에서 로그인한 유저의 username 보내 조회
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<UserDto> getMyUser(@RequestParam String username) {
		return new ResponseEntity<UserDto>(userService.getMyUser(username), HttpStatus.OK);
	}

	@GetMapping("/{username}") // 강사가 학생들 조회 @PathVariable 방식
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<UserDto> getStudent(@PathVariable("username") String username) {
		return new ResponseEntity<UserDto>(userService.getStudent(username), HttpStatus.OK);
	}

	@GetMapping("/allusers") // 강사가 전체 학생 리스트 조회 가능
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<UserDto>> getStudentList() {
		List<UserDto> uDtoList = userService.getStudentList();
		return new ResponseEntity<List<UserDto>>(uDtoList, HttpStatus.OK);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<Boolean> updateUser(UserDto userDto, @RequestParam(value = "file", required = false) MultipartFile mfile) {
		Boolean check = null;
		if (mfile != null) {
			check = userService.updateUser(userDto, mfile);
		} else {
			check = userService.updateUserNoimg(userDto);
		}
		return new ResponseEntity<Boolean>(check, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<Boolean> deleteUser(@RequestParam String username, HttpServletResponse res) {
		return new ResponseEntity<Boolean>(userService.deleteUser(username, res), HttpStatus.NO_CONTENT);
	}

}