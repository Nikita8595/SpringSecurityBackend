package com.example.redit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.Post;
import com.example.redit.model.SubRedit;
import com.example.redit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post , Long> {
	
	List<Post> findAllBySubRedit(SubRedit subRedit);
	
	List<Post> findByUser(User user );
 
}
