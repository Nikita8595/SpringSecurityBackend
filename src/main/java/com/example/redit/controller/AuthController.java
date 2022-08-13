package com.example.redit.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.redit.model.AuthenticationResponse;
import com.example.redit.model.LoginRequest;
import com.example.redit.model.Post;
import com.example.redit.model.RefreshTokenRequest;
import com.example.redit.model.RegisterRequest;
import com.example.redit.model.User;
import com.example.redit.service.AuthService;
import com.example.redit.service.RefershTokenService;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.AllArgsConstructor;
import static org.springframework.http.HttpStatus.OK;
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
//@CrossOrigin(origins = "localhost:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
	
	@Autowired
	private final AuthService authService;
	
	@Autowired
	private final RefershTokenService refreshTokenService;
	
  @PostMapping("/signup")
  public ResponseEntity signUp(@RequestBody RegisterRequest registerRequest) throws Exception {
	  
	  authService.signUp(registerRequest);
	  return new ResponseEntity(HttpStatus.OK);
	  
	  
  }
  @PostMapping(value ="/login" ,consumes=MediaType.APPLICATION_JSON_VALUE)
  public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws InvalidKeyException, Exception {
	
	  return authService.login(loginRequest);
  }
  
  @GetMapping("accountVerification/{token}")
  public ResponseEntity<String> verifiyAccount(@PathVariable String token) throws Exception{
	  authService.verifyAccount(token);
	  return new ResponseEntity<>("Account Activated successfully",HttpStatus.OK);
  }
  
  @PostMapping("refresh/token")
  public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
	  return authService.refreshToken(refreshTokenRequest);
  }
  
  @PostMapping("/logout")
  public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
	  refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
	  return new ResponseEntity<>("Refresh Token deleted  successfully",HttpStatus.OK);
  }
  
	
	
	

	
}
