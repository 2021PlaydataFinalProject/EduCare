package io.educare.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import io.educare.dto.LoginDto;
import io.educare.dto.UserDto;
import io.educare.entity.Instructor;
import io.educare.entity.Student;
import io.educare.entity.User;
import io.educare.jwt.JwtFilter;
import io.educare.jwt.TokenProvider;
import io.educare.repository.UserRepository;
import io.educare.util.CookieUtil;

@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
			AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	public User login(LoginDto loginDto, HttpServletResponse res) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		
		// authenticate(authenticationToken)하면 customeruserdetailsservice의
		// loaduserbyusername 실행됨
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		// 인증 정보를 JwtFilter 클래스의 doFilter 메소드와 유사하게 현재 실행중인 스레드 ( Security Context ) 에
		// 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 해당 인증 정보를 기반으로 jwt 토큰을 생성
		String jwt = tokenProvider.createToken(authentication);
		// jwt 토큰을 쿠키에 넣어서 보내줌
		Cookie accessToken = CookieUtil.createCookie(TokenProvider.AUTHORITIES_KEY, jwt);
		res.addCookie(accessToken);

		Optional<User> userOpt = userRepository.findById(authentication.getName());

		if (userOpt.isPresent()) {
			return userOpt.get();
		} else {
			return null;
		}
	}

	public void logout(HttpServletResponse res) {
		Cookie resetToken = CookieUtil.createCookie(TokenProvider.AUTHORITIES_KEY, null); // 쿠키 auth 값을 null
		resetToken.setMaxAge(0); // 유효시간을 만료시킴
		res.addCookie(resetToken); // 응답 헤더에 추가해서 없어지도록 함
	}

	// 이미 같은 username으로 가입된 유저가 있는 지 확인하고, UserDto 객체의 정보들을 기반으로 권한 객체와 유저 객체를 생성하여
	// Database에 저장
	@Transactional
	public User insertUser(UserDto userDto, MultipartFile mfile) {
		Optional<User> findMyUser = userRepository.findById(userDto.getUsername());

		if (!findMyUser.isPresent()) {
			String imgname = null;

			try {
				imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
				mfile.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + imgname));
				logger.info("{} 가입회원 이미지 등록", userDto.getUsername());
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				logger.error("{} 가입회원 이미지 등록 오류", userDto.getUsername());
			}

			if (userDto.getRole().equals("student")) {

				Student student = new Student();
				student.setUsername(userDto.getUsername());
				student.setPassword(passwordEncoder.encode(userDto.getPassword()));
				student.setUserRealName(userDto.getUserRealname());
				student.setPhoneNumber(userDto.getPhoneNumber());
				student.setUserImage(imgname);

				return userRepository.save(student);
			} else {

				Instructor instructor = new Instructor();
				instructor.setUsername(userDto.getUsername());
				instructor.setPassword(passwordEncoder.encode(userDto.getPassword()));
				instructor.setUserRealName(userDto.getUserRealname());
				instructor.setPhoneNumber(userDto.getPhoneNumber());
				instructor.setUserImage(imgname);

				return userRepository.save(instructor);
			}
		} else {
			logger.error("{} 기존가입 회원가입 오류", userDto.getUsername());
			return null;
		}
	}

	@Transactional
	public User insertUserNoimg(UserDto userDto) {
		Optional<User> findUser = userRepository.findById(userDto.getUsername());

		if (!findUser.isPresent()) {
			if (userDto.getRole().equals("student")) {
				Student student = new Student();
				student.setUsername(userDto.getUsername());
				student.setPassword(passwordEncoder.encode(userDto.getPassword()));
				student.setUserRealName(userDto.getUserRealname());
				student.setPhoneNumber(userDto.getPhoneNumber());
				student.setUserImage("default.png");

				return userRepository.save(student);
			} else {
				Instructor instructor = new Instructor();
				instructor.setUsername(userDto.getUsername());
				instructor.setPassword(passwordEncoder.encode(userDto.getPassword()));
				instructor.setUserRealName(userDto.getUserRealname());
				instructor.setPhoneNumber(userDto.getPhoneNumber());
				instructor.setUserImage("default.png");

				return userRepository.save(instructor);
			}
		} else {
			logger.error("{} 기존가입 회원가입 오류", userDto.getUsername());
			return null;
		}
	}

	public User getMyUser(String username) {

		Optional<User> findMyUser = userRepository.findById(username);

		if (findMyUser.isPresent()) {
			logger.info("{} 회원 조회 요청 성공", username);
			return findMyUser.get();
		} else {
			logger.error("미존재 회원 {} 조회 요청 실패", username);
			return null;
		}
	}

	public User getUser(String username) {

		Optional<User> findMyUser = userRepository.findById(username);

		if (findMyUser.isPresent()) {
			logger.info("{} 회원 조회 요청 성공", username);
			return findMyUser.get();
		} else {
			logger.error("미존재 회원 {} 조회 요청 실패", username);
			return null;
		}
	}

	public List<User> getUserList(String role) {

		logger.info("전체 {} 회원 조회 요청", role);
		return (List<User>) userRepository.findAllUserByRole(role);
	}
	
	@Transactional
	public User updateUser(UserDto userDto, MultipartFile mfile) {

		Optional<User> findUser = userRepository.findById(userDto.getUsername());

		if (findUser.isPresent()) {
			String imgname = null;

			try {
				imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
				mfile.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + imgname));

				String filename = findUser.get().getUserImage();
				File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

				if (file.exists() && !filename.equals("default.png")) {
					if (file.delete()) {
						logger.info("{} 회원 기존 이미지 삭제 완료", userDto.getUsername());
					} else {
						logger.debug("{} 회원 기존 이미지 삭제 실패", userDto.getUsername());
					}
				}
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				logger.error("{} 회원 이미지 갱신 오류 발생", userDto.getUsername());
			}
			User finduser = findUser.get();
			finduser.setUsername(finduser.getUsername());
			finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
			finduser.setUserRealName(userDto.getUserRealname());
			finduser.setPhoneNumber(userDto.getPhoneNumber());
			finduser.setUserImage(imgname);

			return userRepository.save(finduser);
		} else {
			logger.error("미존재 회원 {} 갱신 요청 실패", userDto.getUsername());
			return null;
		}
	}
	
	@Transactional
	public User updateUserNoimg(UserDto userDto) {
		Optional<User> findUser = userRepository.findById(userDto.getUsername());
		if (findUser.isPresent()) {

			User finduser = findUser.get();
			finduser.setUsername(finduser.getUsername());
			finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
			finduser.setUserRealName(userDto.getUserRealname());
			finduser.setPhoneNumber(userDto.getPhoneNumber());

			return userRepository.save(finduser);
		} else {
			logger.error("미존재 회원 {} 갱신 요청 실패", userDto.getUsername());
			return null;
		}
	}
	
	@Transactional
	public void deleteUser(String username, HttpServletResponse res) {

		Optional<User> findUser = userRepository.findById(username);

		if (findUser.isPresent()) {

			String filename = findUser.get().getUserImage();
			File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

			if (file.exists() && !filename.equals("default.png")) {
				if (file.delete()) {
					logger.info("{} 회원 기존 이미지 삭제 완료", findUser.get().getUsername());
				} else {
					logger.error("{} 회원 기존 이미지 삭제 실패", findUser.get().getUsername());
				}
			}
			userRepository.delete(findUser.get());
			// 로그아웃
			Cookie resetToken = CookieUtil.createCookie(TokenProvider.AUTHORITIES_KEY, null); // 쿠키 auth 값을 null
			resetToken.setMaxAge(0); // 유효시간을 만료시킴
			res.addCookie(resetToken); // 응답 헤더에 추가해서 없어지도록 함

			logger.info("{} 회원 탈퇴 완료", username);
		} else {
			logger.error("미존재 회원 {} 탈퇴 요청 실패", username);
		}
	}

}
