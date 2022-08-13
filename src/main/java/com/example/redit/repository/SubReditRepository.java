package com.example.redit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.SubRedit;

@Repository
public interface SubReditRepository extends JpaRepository<SubRedit ,Long>{
	
	Optional<SubRedit> findByName(String name);
	

}
