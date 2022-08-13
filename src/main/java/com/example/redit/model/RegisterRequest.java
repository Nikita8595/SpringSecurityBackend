package com.example.redit.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
	
	@NotNull
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String password;

}
