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
	public ResponseEntity<String> insertUser(UserDto userDto,
			@RequestParam(value = "file", required = false) MultipartFile mfile) {
		Boolean check = null;
		if (mfile != null) {
			check = userService.insertUser(userDto, mfile);
		} else {
			check = userService.insertUserNoimg(userDto);
		}
		
		if (check) {
			return new ResponseEntity<String>("회원 가입 성공" , HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto, HttpServletResponse res) {
		return userService.login(loginDto, res);
	}
	
	@PostMapping("/logout")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<String> logout(HttpServletRequest req) {
		
		if (userService.logout(req)) {
			return new ResponseEntity<String>("회원 로그아웃 성공" , HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>("회원 로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}

	@GetMapping("/myinfo") // 프론트에서 로그인한 유저의 username 보내 조회
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<UserDto> getMyUser(@RequestParam String username) {
		return new ResponseEntity<UserDto>(userService.getMyUser(username), HttpStatus.OK);
	}

	@GetMapping("/stuinfo") // 강사가 학생들 조회 @PathVariable 방식
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<UserDto> getStudent(@RequestParam String username) {
		return new ResponseEntity<UserDto>(userService.getStudent(username), HttpStatus.OK);
	}

	@GetMapping("/allusers") // 강사가 전체 학생 리스트 조회 가능
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<UserDto>> getStudentList() {
		List<UserDto> uDtoList = userService.getStudentList();
		return new ResponseEntity<List<UserDto>>(uDtoList, HttpStatus.OK);
	}
	
	@GetMapping("/notest/{testnum}") 
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	public ResponseEntity<List<UserDto>> getStudentListNotInTest(@PathVariable long testnum) {
		List<UserDto> uDtoList = userService.getStudentListNotInTest(testnum);
		return new ResponseEntity<List<UserDto>>(uDtoList, HttpStatus.OK);
	}

	@PutMapping("/update")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<String> updateUser(UserDto userDto, @RequestParam(value = "file", required = false) MultipartFile mfile) {
		Boolean check = null;
		if (mfile != null) {
			check = userService.updateUser(userDto, mfile);
		} else {
			check = userService.updateUserNoimg(userDto);
		}
		
		if (check) {
			return new ResponseEntity<String>("회원 정보 수정 성공" , HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("회원 정보 수정 실패", HttpStatus.NOT_MODIFIED);			
		}
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
	public ResponseEntity<String> deleteUser(@RequestParam String username) {
		
		if (userService.deleteUser(username)) {
			return new ResponseEntity<String>("회원 탈퇴 성공" , HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>("회원 탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);			
		}		
	}

}