package com.example.redit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.VerificationToken;
@Repository
public interface VerficationTokenRepository extends JpaRepository<VerificationToken , Long> {

	Optional<VerificationToken> findByToken(String token);
}
