package com.example.redit.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.LAZY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="token")
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long id;
	
	private String token;
	
	@OneToOne(fetch =LAZY)
	private User user;
	
	private Instant expiryDate;
	
}
