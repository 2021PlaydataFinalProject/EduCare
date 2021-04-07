package io.educare.service;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.educare.dto.LoginDto;
import io.educare.dto.UserDto;
import io.educare.entity.User;
import io.educare.jwt.JwtFilter;
import io.educare.jwt.TokenProvider;
import io.educare.repository.UserRepository;
import io.educare.util.CookieUtil;
import io.educare.util.SecurityUtil;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

public interface UserService {
	public boolean insertUser(UserDto userDto, MultipartFile mfile);


public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

    @Transactional
    // 이미 같은 username으로 가입된 유저가 있는 지 확인하고, UserDto 객체의 정보들을 기반으로 권한 객체와 유저 객체를 생성하여 Database에 저장
    public User signup(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .realname(userDto.getRealname())
                .role("ROLE_USER")
                .build();
        return userRepository.save(user);
    }
    
    public User login(LoginDto loginDto, HttpServletResponse res) {
    	
    	 UsernamePasswordAuthenticationToken authenticationToken =
                 new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
         //authenticate(authenticationToken)하면 customeruserdetailsservice의 loaduserbyusername 실행됨
         Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
         // 인증 정보를 JwtFilter 클래스의 doFilter 메소드와 유사하게 현재 실행중인 스레드 ( Security Context ) 에 저장
         SecurityContextHolder.getContext().setAuthentication(authentication);
         // 해당 인증 정보를 기반으로 jwt 토큰을 생성
         String jwt = tokenProvider.createToken(authentication);
         // jwt 토큰을 쿠키에 넣어서 보내줌
         Cookie accessToken = CookieUtil.createCookie(TokenProvider.AUTHORITIES_KEY, jwt);
         res.addCookie(accessToken);
         
         Optional<User> userOpt = userRepository.findByUsername(authentication.getName());
         
         if(userOpt.isPresent() && userOpt.get().getPassword().equals(loginDto.getPassword())) {
                 return userOpt.get();
        	 } else { return null; }  
         }
    
    public void logout(HttpServletResponse res) {
		Cookie resetToken = CookieUtil.createCookie(TokenProvider.AUTHORITIES_KEY, null);  // 쿠키 auth 값을 null
		resetToken.setMaxAge(0);  // 유효시간을 만료시킴
		res.addCookie(resetToken); // 응답 헤더에 추가해서 없어지도록 함
	}

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername);
    }
}
