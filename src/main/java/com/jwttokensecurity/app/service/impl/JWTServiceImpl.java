package com.jwttokensecurity.app.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jwttokensecurity.app.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .verifyWith((SecretKey) getSignInKey())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		 return Jwts.builder()
	                .subject(userDetails.getUsername())
	                .issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 + 24))
	                .signWith(getSignInKey())
	                .compact();
	}
	
	@Override
	public String refreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		
		return null;
	}
	
	@Override
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		return null;
	}
	
	private Key getSignInKey() {
		byte[] key = Decoders.BASE64.decode("g5jK8zE2aY1XqRw9LsNp3vT0F7dUJ4Hl6VbM1WQrST8BcZkJp2");
		return Keys.hmacShaKeyFor(key);
	}


}
