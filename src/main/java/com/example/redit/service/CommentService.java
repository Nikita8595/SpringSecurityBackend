package com.example.redit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.model.Comment;
import com.example.redit.model.CommentsDto;
import com.example.redit.model.NotificationEmail;
import com.example.redit.model.Post;
import com.example.redit.model.User;
import com.example.redit.repository.CommentRepository;
import com.example.redit.repository.PostRepository;
import com.example.redit.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

	private static final String POST_URL ="";
	
	private final PostRepository postRepo;
	private final CommentRepository commentRepo;
	private final UserRepository userRepo;
	private final AuthService authService;
	private final MailBuilder mailContentBuilder;
	private final MailService mailService;
	
	public void createComment(CommentsDto commentsDto) throws Exception {
		Post post = postRepo.findById(commentsDto.getPostId())
				.orElseThrow(()->new Exception(commentsDto.getPostId().toString()));
		
		
		Comment comment = mapComment(commentsDto,post, authService.getCurrentUser());
	 commentRepo.save(comment);
		String message = mailContentBuilder.build(post.getUser().getName() +"posted comments on your post" +POST_URL);
	sendCommentNotification(message,post.getUser());
	
	}
	public List<CommentsDto> getCommentByPost(Long postId) throws Exception{
	
		Post post = postRepo.findById(postId)
				.orElseThrow(()-> new Exception(postId.toString()));
		return commentRepo.findByPost(post)
				.stream()
				.map(this::mapToDto)
				.collect(toList());
		
	}
	public List<CommentsDto> getCommentByUser(String username) throws Exception{
		
		User user = userRepo.findByName(username)
				.orElseThrow(()-> new Exception(username));
		return commentRepo.findAllByUser(user)
				.stream()
				.map(this::mapToDto)
				.collect(toList());
		
	}

	private void sendCommentNotification(String message, User user) throws Exception {
		mailService.sendMail(new NotificationEmail(user.getName() +"commented on your post" ,user.getEmail() ,message));
		
	}

	private Comment mapComment(CommentsDto commentsDto, Post post, User currentUser) {
		return Comment.builder()
		.id(commentsDto.getId())
		.text(commentsDto.getText())
		.createdDate(java.time.Instant.now())
		.post(post)
		.user(currentUser)
		.build();
		
	}
	private CommentsDto mapToDto(Comment comment1) {
		return CommentsDto.builder()
				.postId(comment1.getPost().getPostId())
				.username(comment1.getUser().getName())
				.build();
	}
	
	
}
