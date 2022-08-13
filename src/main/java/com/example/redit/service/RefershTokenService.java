package com.example.redit.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.model.RefeshToken;
import com.example.redit.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefershTokenService {
	
	private final RefreshTokenRepository refreshTokenRepo;
	
	RefeshToken generateToken() {
		RefeshToken refreshToken = new RefeshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(Instant.now());
		return refreshTokenRepo.save(refreshToken);
	}
	
	void validateToken(String token) throws Exception {
		refreshTokenRepo.findByToken(token).orElseThrow(()->new Exception("Invalid Refresh Token"));
	}
	
	public void deleteRefreshToken(String token) {
		refreshTokenRepo.deleteByToken(token);
	}

}
