package io.educare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import io.educare.jwt.JwtAccessDeniedHandler;
import io.educare.jwt.JwtAuthenticationEntryPoint;
import io.educare.jwt.JwtSecurityConfig;
import io.educare.jwt.TokenProvider;

// Spring Security를 활성화
@EnableWebSecurity
// @PreAuthorize, @PostAuthorize 애노테이션을 사용하여 인가 처리를 하고 싶을때 사용
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling() //예외처리 기능
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 실패시 처리
                .accessDeniedHandler(jwtAccessDeniedHandler) // 인가 실패시 처리
                .and()
                .sessionManagement() // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()  // Token이 없어도 호출할 수 있도록 허용
                .antMatchers("/user/signin").permitAll()
                .antMatchers("/user/signup").permitAll()
                .antMatchers("/stutest/update-mytest").permitAll()
                .antMatchers("/userimg/**").permitAll()
                .antMatchers("/tproblemvideo/4_ggg@naver.com_video.mp4").permitAll()
                .anyRequest().authenticated() // 나머지는 권한 검증
                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // 사용자 정의 설정
    }
}
