package io.educare.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public ResponseEntity<UserDto> login(LoginDto loginDto, HttpServletResponse res) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		// authenticate(authenticationToken)하면 CustomerUserDetailsService의 loaduserbyusername 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 인증 정보를 JwtFilter 클래스의 doFilter 메소드와 유사하게 현재 실행중인 스레드 (Security Context) 에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 해당 인증 정보를 기반으로 jwt 토큰을 생성
		String jwt = tokenProvider.createToken(authentication);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		Optional<User> userOpt = userRepository.findById(authentication.getName());

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			UserDto userDto = UserDto.builder().username(user.getUsername()).userRealname(user.getUserRealName())
					.phoneNumber(user.getPhoneNumber()).userImage(user.getUserImage()).role(user.getRole()).build();

			logger.info("로그인 유저 정보 요청 성공");
			return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.OK);
		} else {
			logger.info("로그인 유저 정보 요청 실패");
			return null;
		}
	}

	public Boolean logout(HttpServletRequest req) {
		try {
			logger.info("로그아웃 성공");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("로그아웃 실패");
			return false;
		}
	}

	// 이미 같은 username으로 가입된 유저가 있는 지 확인, UserDto 객체의 정보들을 기반 권한 객체와 유저 객체를 생성해 db 저장
	@Transactional
	public Boolean insertUser(UserDto userDto, MultipartFile mfile) {
		Optional<User> findMyUser = userRepository.findById(userDto.getUsername());
		try {
			if (!findMyUser.isPresent()) {
				String imgname = null;

				try {
					imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
					mfile.transferTo(
							new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + imgname));
					logger.info("{} 가입회원 이미지 등록", userDto.getUsername());
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					logger.error("{} 가입회원 이미지 등록 실패", userDto.getUsername());
				}

				if (userDto.getRole().equals("student")) {					
					Student student = new Student();
					student.setUsername(userDto.getUsername());
					student.setPassword(passwordEncoder.encode(userDto.getPassword()));
					student.setUserRealName(userDto.getUserRealname());
					student.setPhoneNumber(userDto.getPhoneNumber());
					student.setUserImage(imgname);

					userRepository.save(student);
					logger.info("{} 학생 회원가입 성공", userDto.getUsername());
					return true;
				} else if (userDto.getRole().equals("instructor")) {
					Instructor instructor = new Instructor();
					instructor.setUsername(userDto.getUsername());
					instructor.setPassword(passwordEncoder.encode(userDto.getPassword()));
					instructor.setUserRealName(userDto.getUserRealname());
					instructor.setPhoneNumber(userDto.getPhoneNumber());
					instructor.setUserImage(imgname);

					userRepository.save(instructor);
					logger.info("{} 강사 회원가입 성공", userDto.getUsername());
					return true;
				} else {
					logger.info("{} 학생, 강사가 아닙니다. 회원가입 실패", userDto.getUsername());
					return false;
				}
			} else {
				logger.info("{} 기존가입 회원가입 실패", userDto.getUsername());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 회원가입 실패", userDto.getUsername());
			return false;
		}
	}

	@Transactional
	public Boolean insertUserNoimg(UserDto userDto) {
		Optional<User> findUser = userRepository.findById(userDto.getUsername());

		try {
			if (!findUser.isPresent()) {
				if (userDto.getRole().equals("student")) {
					Student student = new Student();
					student.setUsername(userDto.getUsername());
					student.setPassword(passwordEncoder.encode(userDto.getPassword()));
					student.setUserRealName(userDto.getUserRealname());
					student.setPhoneNumber(userDto.getPhoneNumber());
					student.setUserImage("default.png");

					userRepository.save(student);
					logger.info("{} 학생 회원가입 성공", userDto.getUsername());
					return true;
				} else if (userDto.getRole().equals("instructor")) {
					Instructor instructor = new Instructor();
					instructor.setUsername(userDto.getUsername());
					instructor.setPassword(passwordEncoder.encode(userDto.getPassword()));
					instructor.setUserRealName(userDto.getUserRealname());
					instructor.setPhoneNumber(userDto.getPhoneNumber());
					instructor.setUserImage("default.png");

					userRepository.save(instructor);
					logger.info("{} 강사 회원가입 성공", userDto.getUsername());
					return true;
				} else {
					logger.info("{} 학생, 강사가 아닙니다. 회원가입 실패", userDto.getUsername());
					return false;
				}
			} else {
				logger.info("{} 기존가입 회원가입 실패", userDto.getUsername());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{} 회원가입 실패", userDto.getUsername());
			return false;
		}
	}

	public UserDto getMyUser(String username) {
		Optional<User> findMyUser = userRepository.findById(username);

		if (findMyUser.isPresent()) {
			User user = findMyUser.get();
			UserDto userDto = UserDto.builder().username(user.getUsername()).userRealname(user.getUserRealName())
					.phoneNumber(user.getPhoneNumber()).userImage(user.getUserImage()).role(user.getRole()).build();

			logger.info("{} 회원 조회 요청 성공", username);
			return userDto;
		} else {
			logger.info("미존재 회원 {} 조회 요청 실패", username);
			return null;
		}
	}

	public UserDto getStudent(String username) {
		Optional<User> findMyUser = userRepository.findById(username);

		if (findMyUser.isPresent()) {
			User user = findMyUser.get();
			UserDto userDto = UserDto.builder().username(user.getUsername()).userRealname(user.getUserRealName())
					.phoneNumber(user.getPhoneNumber()).userImage(user.getUserImage()).role(user.getRole()).build();

			logger.info("{} 회원 조회 요청 성공", username);
			return userDto;
		} else {
			logger.info("미존재 회원 {} 조회 요청 실패", username);
			return null;
		}
	}

	public List<UserDto> getStudentList() {
		List<User> userList = userRepository.findAllUserByRole("ROLE_STUDENT");
		List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUsername(), null, u.getUserRealName(),
				u.getPhoneNumber(), u.getUserImage(), u.getRole())).collect(Collectors.toList());

		logger.info("전체 학생 회원 조회");
		return uDtoList;
	}
	
	public List<UserDto> getStudentListNotInTest(long testnum) {
		List<User> userList = userRepository.findAllUserNotInTest(testnum, "ROLE_STUDENT");
		List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUsername(), null, u.getUserRealName(),
				u.getPhoneNumber(), u.getUserImage(), u.getRole())).collect(Collectors.toList());

		logger.info("전체 학생 회원 조회");
		return uDtoList;
	}

	@Transactional
	public Boolean updateUser(UserDto userDto, MultipartFile mfile) {
		Optional<User> findUser = userRepository.findById(userDto.getUsername());

		try {
			if (findUser.isPresent()) {
				String imgname = null;

				try {
					imgname = String.valueOf(System.currentTimeMillis()) + mfile.getOriginalFilename();
					mfile.transferTo(
							new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + imgname));

					String filename = findUser.get().getUserImage();
					File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

					if (file.exists() && !filename.equals("default.png")) {
						if (file.delete()) {
							logger.info("{} 회원 기존 이미지 삭제 완료", userDto.getUsername());
						} else {
							logger.info("{} 회원 기존 이미지 삭제 실패", userDto.getUsername());
						}
					}
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					logger.error("{} 회원 이미지 갱신 오류 발생", userDto.getUsername());
					return false;
				}
				User finduser = findUser.get();
				finduser.setUsername(finduser.getUsername());
				finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
				finduser.setUserRealName(userDto.getUserRealname());
				finduser.setPhoneNumber(userDto.getPhoneNumber());
				finduser.setUserImage(imgname);

				userRepository.save(finduser);
				logger.info("{} 회원 정보 수정 성공", userDto.getUsername());
				return true;
			} else {
				logger.info("미존재 회원 {} 정보 수정 실패", userDto.getUsername());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("미존재 회원 {} 정보 수정 실패", userDto.getUsername());
			return false;
		}
	}

	@Transactional
	public Boolean updateUserNoimg(UserDto userDto) {
		Optional<User> findUser = userRepository.findById(userDto.getUsername());

		try {
			if (findUser.isPresent()) {
				User finduser = findUser.get();
				finduser.setUsername(finduser.getUsername());
				finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
				finduser.setUserRealName(userDto.getUserRealname());
				finduser.setPhoneNumber(userDto.getPhoneNumber());

				userRepository.save(finduser);
				logger.info("{} 회원 정보 수정 성공", userDto.getUsername());
				return true;
			} else {
				logger.info("미존재 회원 {} 정보 수정 실패", userDto.getUsername());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("미존재 회원 {} 정보 수정 실패", userDto.getUsername());
			return false;
		}
	}

	@Transactional
	public Boolean deleteUser(String username) {
		Optional<User> findUser = userRepository.findById(username);

		try {
			if (findUser.isPresent()) {
				String filename = findUser.get().getUserImage();
				File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

				if (file.exists() && !filename.equals("default.png")) {
					if (file.delete()) {
						logger.info("{} 회원 기존 이미지 삭제 완료", findUser.get().getUsername());
					} else {
						logger.info("{} 회원 기존 이미지 삭제 실패", findUser.get().getUsername());
					}
				}
				userRepository.delete(findUser.get());
				logger.info("{} 회원 탈퇴 완료", username);
				return true;
			} else {
				logger.info("미존재 회원 {} 탈퇴 실패", username);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("회원 {} 탈퇴 실패", username);
			return false;
		}
	}

}
