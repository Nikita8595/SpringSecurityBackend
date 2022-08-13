package com.example.redit.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
	private String authenticationToken;
	private Instant expiresAt;
	private String refreshToken; 
	private String username;

}
