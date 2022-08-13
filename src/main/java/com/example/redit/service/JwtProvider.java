package com.example.redit.service;

import java.io.InputStream;


import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;


import static io.jsonwebtoken.Jwts.parser;
@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}")
	private Long jwtExpire;
	
	
	@PostConstruct
	public void init() throws Exception {
		
		try {
			keyStore = 	KeyStore.getInstance("JKS");
			InputStream resourceStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceStream,"secret".toCharArray());
			
		}
		catch(Exception e) {
			throw new Exception("Exception while loading keystore");
		}
		
	}
	
	public String generateToken(Authentication authentication) throws InvalidKeyException, Exception {
		
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername())
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpire)))
				.compact();
		
		
	}
	private PrivateKey getPrivateKey() throws Exception {
		try {
			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
			
		}
		catch(Exception e) {
			throw new Exception("Exception occur while from keystore");
		}
	}
	
	public boolean validateToken(String jwt) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
		
		
	}

	private PublicKey getPublicKey() throws Exception {
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
			
		}
		catch(Exception e) {
			throw new Exception("exception while getting public key");
		}
	
	}
	
	public String getUserNameForJwt(String token) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, Exception {
		Claims claims = parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public String generateTokenWithUsername(String username) throws InvalidKeyException, Exception {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpire)))
                .compact();
	}
	
	public Long getJwtExpirationInMills() {
		return jwtExpire;
	}

}
