package com.example.redit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.model.Post;
import com.example.redit.model.Vote;
import com.example.redit.model.VoteDto;
import com.example.redit.repository.PostRepository;
import com.example.redit.repository.VoteRepository;
import static com.example.redit.model.VoteType.UPVOTE;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class VoteService {
	
	private final VoteRepository voteRepo;
	private final PostRepository postRepo;
	private final AuthService authService;
	
	@Transactional
	public void vote(VoteDto voteDto) throws Exception {
		Post post = postRepo.findById(voteDto.getPostId())
				.orElseThrow(()->new Exception("post not found with id"+voteDto.getPostId()));
	
		Optional<Vote> voteByPostUser = voteRepo.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if(voteByPostUser.isPresent() && voteByPostUser.get().getVoteId().equals(voteDto.getVoteType()))
		{
			throw new Exception("You have already" + voteDto.getVoteType() +"'d for this post");
		}
		if(post.getVoteCount() == null) {
			post.setVoteCount(0);
			voteRepo.save(mapToVote(voteDto,post));
			postRepo.save(post);
			
		}
		if(UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount()+1);
			
		}
		else {
			post.setVoteCount(post.getVoteCount()-1);
		}
		
		voteRepo.save(mapToVote(voteDto,post));
		postRepo.save(post);
		
	}

	private Vote mapToVote(VoteDto voteDto, Post post) throws Exception {
		
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
	
	

}
