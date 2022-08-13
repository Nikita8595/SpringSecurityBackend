package com.example.redit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
 
	Optional<User> findByName(String userName);

}
