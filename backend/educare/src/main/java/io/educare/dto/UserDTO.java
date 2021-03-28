package io.educare.dto;


import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.educare.domain.User;
import io.educare.domain.UserImg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class UserDTO {

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	@Builder
	public static class SigninRequest {
		private String userId;
		private String pw;
		private Long userNum;

		
		public static SigninResponse of(User user) {
			return SigninResponse.builder().userNum(user.getUserNum()).userId(user.getUserId()).name(user.getName()).build();
		}
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	@Builder
	public static class SigninResponse {
		private Long userNum;
		private String userId;
		private String name;

		public static SigninResponse of(User user) {
			return SigninResponse.builder().userNum(user.getUserNum()).userId(user.getUserId()).name(user.getName()).build();
		}
	}
	
	// 회원가입을 위한 DTO 
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Builder
	public static class UserRegister {
		private String userId;
		private String pw;
		private String name;
		private String dob;
		private String job;
		private int pageStatus;
		private LocalDate suDate;

		static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		
		public static User toEntity(UserRegister user) {
			return User.builder().userId(user.getUserId()).pw(passwordEncoder.encode(user.getPw())).name(user.getName()).dob(user.getDob())
					.job(user.getJob()).suDate(LocalDate.now()).pageStatus(user.getPageStatus())
					.build();
		}
	}

	// 회원 1인의 전체 정보 불러오기 
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserInfo {
		private String userId;
		private String name;
		private String dob;
		private String job;
		private int pageStatus;
		private LocalDate suDate;
		private List<UserImg> userImg;
		private int revCnt;

		public static UserInfo of(User user) {
			return UserInfo.builder()
					.userId(user.getUserId())
					.dob(user.getDob())
					.name(user.getName())
					.job(user.getJob())
					.pageStatus(user.getPageStatus())
					.suDate(user.getSuDate())
					.userImg(user.getUserImg()).build();
		}
	}

	// 회원 이미지와 간단 설명
	@NoArgsConstructor
	@AllArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class UserThumbnail {
		private Long userNum;
		private String userId;
		private String name;
		private int pageStatus;
		private UserImg userImg;

		public static UserThumbnail of(User user) {
			UserImg umg = null;
			if (user.getUserImg().size() > 0) {
				umg = user.getUserImg().get(0);
			}
			return UserThumbnail.builder().userNum(user.getUserNum()).userId(user.getUserId()).name(user.getName())
					.pageStatus(user.getPageStatus()).userImg(umg)
					.build();
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class IdCheckResult {
		private boolean isExist;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class	UserSimple {
		private Long userNum;
		private String userName;

	}
}
