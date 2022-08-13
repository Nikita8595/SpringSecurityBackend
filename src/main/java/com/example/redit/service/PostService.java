package com.example.redit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.model.Post;
import com.example.redit.model.PostRequest;
import com.example.redit.model.PostResponse;
import com.example.redit.model.SubRedit;
import com.example.redit.model.User;
import com.example.redit.repository.PostRepository;
import com.example.redit.repository.SubReditRepository;
import com.example.redit.repository.UserRepository;
import static java.util.stream.Collectors.toList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
	private final SubReditRepository subReditRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	
	@Transactional(readOnly = true)
	public PostResponse postResponse(Long id) throws Exception {
		Post post = postRepository.findById(id).orElseThrow(()-> new Exception(id.toString()));
		return mapToDto(post);
		
	}

	private PostResponse mapToDto(Post post) {
		return PostResponse.builder().id(post.getPostId())
                  .postName(post.getPostName())
                  .description(post.getDescription())
                  .url(post.getUrl())
                  .subReditName(post.getSubRedit().getName())
                  .userName(post.getUser().getName())
                  .voteCount(post.getVoteCount())
                  .build();
                  
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPost(){
		return postRepository.findAll()
				.stream()
				.map(this :: mapToDto)
				.collect(toList());
	}
	
	public void save(PostRequest postRequest) throws Exception {
		
		SubRedit subRedit = subReditRepository.findByName(postRequest.getSubReditName())
				.orElseThrow(()-> new Exception(postRequest.getPostName()));
		
		postRepository.save(map(postRequest , subRedit,authService.getCurrentUser()));	
						
				
	}
	
	@Transactional(readOnly =true)
	public List<PostResponse> getPostBySubRedit(Long subReditId) throws Exception{
		SubRedit subRedit = subReditRepository.findById(subReditId)
				.orElseThrow(()-> new Exception(subReditId.toString()));
		
		List<Post> post = postRepository.findAllBySubRedit(subRedit);
		return post.stream().map(this::mapToDto).collect(toList());
		
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostByUserName(String username) throws Exception{
		User user = userRepository.findByName(username).orElseThrow(()->new Exception(username));
		
		return postRepository.findByUser(user)
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}
	
	private Post map(PostRequest postRequest, SubRedit subRedit, User currentUser) {
		
		 return Post.builder().createdDate(java.time.Instant.now())
		.subRedit(subRedit)
		.user(currentUser)
		.description(postRequest.getDescription())
		.postName(postRequest.getPostName())
		.build();
		
	}

}
