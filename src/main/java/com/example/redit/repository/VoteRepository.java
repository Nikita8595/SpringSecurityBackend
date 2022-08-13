package com.example.redit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.Post;
import com.example.redit.model.User;
import com.example.redit.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote , Long> {

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post , User user);
	
        
	
}
