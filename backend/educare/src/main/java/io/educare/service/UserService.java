package io.educare.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import io.educare.dao.UserImgRepository;
import io.educare.dao.UserRepository;
import io.educare.domain.User;
import io.educare.domain.UserImg;
import io.educare.dto.UserDTO.SigninRequest;
import io.educare.dto.UserDTO.SigninResponse;
import io.educare.dto.UserDTO.UserInfo;
import io.educare.dto.UserDTO.UserRegister;
import io.educare.dto.UserDTO.UserThumbnail;
import io.educare.util.CookieUtil;
import io.educare.util.JwtUtil;

@Service
public class UserService {
   @Autowired
   UserRepository userDAO;
   @Autowired
   UserImgRepository userImgDAO;
   @Autowired
   JwtUtil jwtUtil;
   
   /**
    * 아이디과 비밀번호가 일치하는 유저를 조회한다. 해당 아이디 유저가 존재하지 않거나 비밀번호가 일치하지 않으면 Exception
    * 
    * @param 아이디
    * @param pw  비밀번호
    * @return 아이디 비밀번호가 일치하는 유저
    */
   public SigninResponse getMatchedUser(SigninRequest sign, HttpServletResponse res) {

      User signin = userDAO.findUserByUserId(sign.getUserId());
      
      PasswordEncoder encoder = new BCryptPasswordEncoder();
      System.out.println("DB에 있는 암호화된 PW : "+signin.getPw());
      System.out.println("USER로부터 입력받은 PW : "+sign.getPw());
      // 없는 유저
      if (signin == null || !encoder.matches(sign.getPw(),signin.getPw())) {
         return null;
      }

      final String token = jwtUtil.generateToken(signin);
      Cookie accessToken = CookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
      res.addCookie(accessToken);
      
      JsonObject obj =new JsonObject();
       JsonObject data = new JsonObject();

       data.addProperty("status", "success");
       data.addProperty("message", "로그인에 성공했습니다.");
       data.addProperty("token", token);
       
       obj.add("data", data);
      
       System.out.println(obj.toString());
       
      
      return SigninResponse.of(signin);
   }
   
   public void logout(HttpServletResponse res) {
		Cookie deleteToken = CookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, null);// 쿠키 이름에 대한 값을 null로 지정
		deleteToken.setMaxAge(0); // 유효시간을 0으로 설정
		res.addCookie(deleteToken); // 응답 헤더에 추가해서 없어지도록 함
	}

   // 회원 정보 수정
 	public User updateUser(UserRegister user, Long userNum) {
 		User us = userDAO.findByUserNum(userNum); //
 		us.setName(user.getName());
 		us.setPw(user.getPw());
 		us.setDob(user.getDob());
 		us.setJob(user.getJob());
 		us.setPageStatus(user.getPageStatus());
 		
 		return us;
 	}
 	
 	
 	// 회원 이미지 삭제
	public void deleteUserImg(Long id) {
		userImgDAO.deleteById(id);
	}

	// 회원 삭제
	public void deleteUser(Long userNum) {
		User us = userDAO.findByUserNum(userNum); 
		us.getUserImg().forEach(img -> userImgDAO.delete(img));
		userDAO.delete(us);
		}	
	
	// 회원 정보 삭제
	public void deleteUserStandard(Long id) {
		//구현 필요
		}	
	
	// 회원 정보 조회
	public Optional<User> getUser(Long userNum) {
		return userDAO.findById(userNum);
	}

	// 회원 1인 관련 정보페이지에 필요한 DTO를 생성해서 controller에 보낸다.
	public UserInfo getUserInfo(Long userNum) {
		return UserInfo.of(userDAO.findById(userNum).get());
	}

	// 팔로우, 팔로잉, 추천 계정 등에 들어갈 간략한 유저 정보 및 Thumbnail을 불러와 controller에 보낸다.
	public UserThumbnail getUserThumbnail(User user) {
		return UserThumbnail.of(userDAO.findById(user.getUserNum()).get());
	}

	// 회원가입
	public User insertUser(UserRegister user) {
		return userDAO.save(UserRegister.toEntity(user));
	};

	// MultipartFile -> entity -> SQL저장
	public UserImg insertUserImg(MultipartFile userImg, User user) {
		return userImgDAO.save(UserImg.of(userImg, user));
	}

	// MultipartFile -> 저장
	public void saveImg(MultipartFile file, UserImg user) throws IOException {
		// parent directory를 찾는다.
		Path directory = Paths.get(user.getPath()).toAbsolutePath().normalize();

		// directory 해당 경로까지 디렉토리를 모두 만든다.
		Files.createDirectories(directory);

		// 파일명을 바르게 수정한다.
		String fileName = StringUtils.cleanPath(user.getName());

		// 파일명에 '..' 문자가 들어 있다면 오류를 발생하고 아니라면 진행(해킹및 오류방지)
		Assert.state(!fileName.contains(".."), "Name of file cannot contain '..'");
		// 파일을 저장할 경로를 Path 객체로 받는다.
		Path targetPath = directory.resolve(fileName).normalize();

		// 파일이 이미 존재하는지 확인하여 존재한다면 오류를 발생하고 없다면 저장한다.
		Assert.state(!Files.exists(targetPath), fileName + " File alerdy exists.");
		file.transferTo(targetPath);
	}


	// 해당 아이디의유저가 매긴 리뷰개수를 반환한다.
	public Long getUserReviewCounts(Long userNum) {
		return userDAO.findcountOrderByUserNum(userNum);
		// return getUserReviewCounts(userNum);
	}

	// 해당아이디가 작성한 리스트의 개수를 반환한다.
	public Long getUserListCounts(Long userNum) {
		return userDAO.findcountListByUserNum(userNum);
		// return getUserListCounts(userNum);
	}

	// 유저가 선호하는 음식종류 상위 3개를 반환한다.
	public List<String> getFoodTypeByReview(Long userNum) {
		return userDAO.findListByUserNumNFoodType(userNum);
	}

}
