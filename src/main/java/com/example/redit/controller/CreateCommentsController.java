package com.example.redit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.redit.model.CommentsDto;
import com.example.redit.service.CommentService;
import static org.springframework.http.ResponseEntity.status;
import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreateCommentsController {

	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) throws Exception{
		commentService.createComment(commentsDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("postforcomment")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam  Long postId) throws Exception{
		return status(HttpStatus.OK)
				.body(commentService.getCommentByPost(postId));
	}
	@GetMapping("by-username")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForUserName(@RequestParam String userName) throws Exception{
		return status(HttpStatus.OK)
				.body(commentService.getCommentByUser(userName));
	}
	
	
	
}
