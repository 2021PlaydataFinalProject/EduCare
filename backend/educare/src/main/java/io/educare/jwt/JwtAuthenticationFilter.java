package io.educare.jwt;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.educare.util.JwtUtil;
import io.jsonwebtoken.Claims;

//스프링 시큐리티에 UsernamePasswordAuthenticationFilter가 있음
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.


public class JwtAuthenticationFilter extends BasicAuthenticationFilter{

	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		Authentication authentication = getAuthentication(request);
		
		if (authentication != null) {
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(authentication);
		}
		
		//JWT
		chain.doFilter(request, response); //authentication있는지 없는지 상관없이 doFilter 함수 호출
	}
	
	private Authentication getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		if (token == null) {
			return null;
		}

//		Authorization: Bearer TOKEN.sadfasd..에서 앞에 Bearer 제거
		
		//JWUtil에서 Claims 얻기
		Claims claims =jwtUtil.getClaims(token.substring("Bearer".length())); 
		
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(claims, null);
		return authentication;
	}
}
	
	
//	private final AuthenticationManager authenticationManager;
//	
//	// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
//	// 인증 요청시에 실행되는 함수 => /login
//	// attemptAuthentication은 /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		//1. username, password 받아서
//		//2. 정상인지 로그인 시도를 해보기. authenticationManager로 로그인 시도를 하면!!
//		//3. PrincipalDetatilsService가 호출 -> loadUserByUsername() 함수 실행됨
//		//4. PrincipalDetatils를 세션에 담고 (담는이유: 권한 관리를 해주기 위함 ADMIN/USER)
//		//5. JWT 토큰을 만들어서 응답해주면 됨.
//		System.out.println("JwtAuthenticationFilter : 진입");
//		
//		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
//		ObjectMapper om = new ObjectMapper();
//		LoginRequestDto loginRequestDto = null;
//		try {
//			loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("JwtAuthenticationFilter : "+loginRequestDto);
//		
//		// 유저아이디패스워드 토큰 생성
//		UsernamePasswordAuthenticationToken authenticationToken = 
//				new UsernamePasswordAuthenticationToken(
//						loginRequestDto.getUserId(), 
//						loginRequestDto.getPw());
//		
//		System.out.println("JwtAuthenticationFilter : 토큰생성완료");
//		
//		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
//		// loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
//		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
//		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
//		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
//		
//		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
//		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
//		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
//		// authenticationManager.authenticate()을 실행하면 PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨
//		// authentication에는 내가 로그인한 정보가 담김 
//		Authentication authentication = 
//				authenticationManager.authenticate(authenticationToken);
//		
//		//authentication 객체가 session 영역에 저장됨. => 출력이 된다면 로그인이 되었다는 뜻 
//		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
//		System.out.println("Authentication : "+principalDetailis.getUser().getUserId());
//		return authentication;
//	}
//
//	// JWT Token 생성해서 response에 담아주기
//	//RSA 방식은 아닌 HASH 암호 방식
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		
//		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();
//		
//		String jwtToken = JWT.create()
//				.withSubject(principalDetailis.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
//				.withClaim("id", principalDetailis.getUser().getUserId())
//				.withClaim("username", principalDetailis.getUser().getName())
//				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
//		
//		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
//	}
//	
//}
