package com.example.redit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redit.model.RegisterRequest;
import com.example.redit.service.AuthService;

import lombok.AllArgsConstructor;
@RestController("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class checkController {
	
	 @PostMapping("/check")
	  public ResponseEntity signUp() throws Exception {
		  
		
		  return new ResponseEntity(HttpStatus.OK);
		  
		  
	  }

}
