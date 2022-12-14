package com.example.redit.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.Comment;
import com.example.redit.model.Post;
import com.example.redit.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {
	
	List<Comment> findByPost(Post post);
	
	List<Comment> findAllByUser(User user);

}
