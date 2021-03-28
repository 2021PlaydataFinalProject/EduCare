package io.educare.util;


import java.security.Key;

import io.educare.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	final static public String ACCESS_TOKEN_NAME = "accessToken";
	 
	public final static long TOKEN_VALIDATION_SECOND = 86400000;
	 
	private Key key;

	public JwtUtil(String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String createToken(Long user_num, String user_id, String name) {

		return Jwts.builder()
				.claim("user_num", user_num)
				.claim("user_id", user_id)
				.claim("name", name)
				.signWith(key , SignatureAlgorithm.HS256)
				.compact();//"header.payload.signature"
	}

	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
	}

	public String generateToken(User user) {
		
		return Jwts.builder()
				.claim("user_num", user.getUserNum())
				.claim("user_id", user.getUserId())
				.claim("name", user.getName())
				.signWith(key , SignatureAlgorithm.HS256)
				.compact();//"header.payload.signature"
	}
	
}
