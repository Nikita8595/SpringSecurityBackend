package com.example.redit.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.redit.model.User;
import com.example.redit.repository.UserRepository;
import static java.util.Collections.singletonList;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepo;
	
	@Transactional(readOnly =true)
	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User>  userOptional = userRepo.findByName(username);
		User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("No user"+"found with username"+username));
		
		 return new org.springframework.security
	                .core.userdetails.User(user.getName(),user.getPassword(),
	                		user.isEnabled(),true,true,true, getAuthorities("USER"));
		 
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		
		return singletonList(new SimpleGrantedAuthority(role));
	}
	
	
	

}
