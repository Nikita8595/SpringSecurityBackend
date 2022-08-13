package com.example.redit.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redit.model.SubReditDto;
import com.example.redit.service.SubReditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/subreddit")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubReditController {
	
	@Autowired
	private SubReditService subReditService;
	
	@GetMapping
	public List<SubReditDto> getAllSubredit(){
		return subReditService.getAll();
	}
	
	@GetMapping("/{id}")
	public SubReditDto getSubRedit(@PathVariable Long id) throws Exception {
		return subReditService.getSubRedit(id);
	}
	
	@PostMapping(value = "/create-subredit",consumes=MediaType.APPLICATION_JSON_VALUE)
	public SubReditDto create(@RequestBody @Valid SubReditDto subReditDto ) throws Exception {
		return subReditService.save(subReditDto); 
	}

}
