package io.educare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(UserDto userDto) {  //@RequestBody
        return ResponseEntity.ok(userService.signup(userDto));
    }
    
    @PostMapping("/login") //로그인해서 이사람의 role에 어떻게 접근
    public ResponseEntity<User> login(@RequestBody LoginDto loginDto, HttpServletResponse res) {
        return new ResponseEntity<User>(userService.login(loginDto, res), HttpStatus.OK);
    }
    
    @PostMapping("/logout")
	public void logout(HttpServletResponse res, HttpServletRequest req) {
		userService.logout(res);
	}

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
    // 현재 Security Context에 저장되어 있는 인증 정보의 username을 기준으로 한 유저 정보를 리턴
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/info/{username}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    // username을 파라미터로 받아 해당 username의 유저 정보 및 권한 정보를 리턴합니다. 
    // ROLE_ADMIN 권한을 소유한 토큰 가질때만 호출 가능
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
