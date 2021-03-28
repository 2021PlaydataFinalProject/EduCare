package io.educare.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.educare.dto.UserDTO;
import io.educare.dto.UserDTO.SigninResponse;
import io.educare.service.UserService;
import io.educare.util.CookieUtil;
import io.educare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@Value("${jwt.secret}")
	private String secret;

	@PostMapping("/auth/login")
	public SigninResponse signin(@RequestBody @Validated UserDTO.SigninRequest request, HttpServletResponse res) {
		log.info("로그인 시도 : " + request.getUserId());
		try {
			return userService.getMatchedUser(request, res);
		} catch (Exception e) {
			return null;
		}
	}

	@PostMapping("/auth/logout")
	public void logout(HttpServletResponse res, HttpServletRequest hsp) {
		Claims claim = new JwtUtil(secret).getClaims(CookieUtil.getCookie(hsp, "accessToken").getValue());
		Long userNum = claim.get("user_num", Long.class);
		log.info("로그아웃 : " + userNum);
		userService.logout(res);
	}
}
