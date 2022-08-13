package com.example.redit.model;

import java.time.Instant;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import static javax.persistence.GenerationType.SEQUENCE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long userId;
	
	@NotBlank(message ="user name is required")
	private String name;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@Email
	@NotBlank(message = "email cannot be empty")
	private String email;
	
	private Instant Created;
	
	private boolean enabled;
	
	

}
