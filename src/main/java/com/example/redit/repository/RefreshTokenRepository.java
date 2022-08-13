package com.example.redit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.RefeshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefeshToken , Long>{
	
		Optional<RefeshToken> findByToken(String token);
		 void deleteByToken(String token);
		 


}
