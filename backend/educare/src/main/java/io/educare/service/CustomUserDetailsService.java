package io.educare.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.educare.entity.User;
import io.educare.repository.UserRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   @Transactional // 쿼리 도중 에러가 발생시 rollback 
   // 로그인 메소드를 수행할때 Database에서 User 정보를 조회
   public UserDetails loadUserByUsername(final String username) {
	   Optional<User> userEntity = userRepository.findByUsername(username);
		return new CustomUserDetails(userEntity.get());
   }

   private CustomUserDetails createUser(User user) {
      return new CustomUserDetails(user);
   }
}
