package com.example.redit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.model.AuthenticationResponse;
import com.example.redit.model.LoginRequest;
import com.example.redit.model.NotificationEmail;
import com.example.redit.model.RefreshTokenRequest;
import com.example.redit.model.RegisterRequest;
import com.example.redit.model.User;
import com.example.redit.model.VerificationToken;
import com.example.redit.repository.UserRepository;
import com.example.redit.repository.VerficationTokenRepository;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.time.Instant.now;

import java.time.Instant;

import static com.example.redit.constant.Constants.ACTIVATION_EMAIL;

import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
@Slf4j
public class AuthService  {
	
	@Autowired
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final VerficationTokenRepository verificationRepo; 
	private final MailBuilder mailbuilder;
	private final MailService mailService;
	private AuthenticationManager authenticationManager;
	
	private JwtProvider jwtProvider;
	
	private final RefershTokenService refreshTokenService;
	
	@Transactional
	public void signUp(RegisterRequest registerRequest) throws Exception {
		User user = new User();
		
		user.setName(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setCreated(now());
		user.setEnabled(false);
		userRepo.save(user);
		
		String token = generateVerificationToken(user);
		
		String message = mailbuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account :"
		+ ACTIVATION_EMAIL +"/"+token);
		
		mailService.sendMail(new NotificationEmail("please activate you account",user.getEmail(),message));
	}
	
	private String encodePassword(String str) {
		return passwordEncoder.encode(str);
	}
	
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationRepo.save(verificationToken);
		return token;
	}
	
	public void verifyAccount(String token) throws Exception {
		Optional<VerificationToken> verficationTokenOptional = verificationRepo.findByToken(token);
		verficationTokenOptional.orElseThrow(()->new Exception("invalid Token"));
		fetchUserEnable(verficationTokenOptional.get());
		
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) throws InvalidKeyException, Exception {
		
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword())
				
				) ;
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String authenticationToken = jwtProvider.generateToken(authenticate);
		String token =jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder().authenticationToken(token)
				.refreshToken(refreshTokenService.generateToken().getToken())
			.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMills()))
			.username(loginRequest.getUsername())
			.build();
			
		
	}
	
	@Transactional
	public void fetchUserEnable(VerificationToken verificationToken) throws Exception {
		String username = verificationToken.getUser().getName();
		User user = userRepo.findByName(username).orElseThrow(()->new Exception("user not  found"+username));
	     user.setEnabled(true);
	     userRepo.save(user);
	}

	@Transactional
	public User getCurrentUser() throws Exception {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return userRepo.findByName(principal.getUsername())
				.orElseThrow(()-> new Exception("user not found" + principal.getUsername()));
		
		
	}
	
	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
		refreshTokenService.validateToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMills()))
				.username(refreshTokenRequest.getUsername())
				.build();
				
		
	}

}
