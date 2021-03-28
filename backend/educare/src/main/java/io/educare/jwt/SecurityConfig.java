package io.educare.jwt;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.educare.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{	
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		Filter filter = new JwtAuthenticationFilter(
				authenticationManager(), jwtUtil());
		
		http
				.cors().disable()
				.csrf().disable()
				.formLogin().disable()
				.headers().frameOptions().disable()
				.and()
				.addFilter(filter)
				.sessionManagement() // 세션 관리하기 위함
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션에 대한 정책을 정함 -> 세션 관리하지 않음(STATELESS) -> 필터로 관리함
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
		
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil(secret);
	}
}