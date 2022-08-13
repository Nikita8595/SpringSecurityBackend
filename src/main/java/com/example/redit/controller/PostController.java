package com.example.redit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redit.model.PostRequest;
import com.example.redit.model.PostResponse;
import com.example.redit.service.PostService;
import static org.springframework.http.ResponseEntity.status;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

	@Autowired
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws Exception{
		
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPost(){
	
		return status(HttpStatus.OK).body(postService.getAllPost());
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) throws Exception{
	
		return status(HttpStatus.OK).body(postService.postResponse(id));
		
	}
	@GetMapping("/by-subredit/{id}")
	public ResponseEntity<List<PostResponse>> getPostBySubRedit(@PathVariable Long id) throws Exception{
	
		return status(HttpStatus.OK).body(postService.getPostBySubRedit(id));
		
		
	}
	
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable String name) throws Exception{
	
		return status(HttpStatus.OK).body(postService.getPostByUserName(name));
		
	}
	
}
