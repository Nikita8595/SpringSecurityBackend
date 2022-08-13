package com.example.redit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.model.SubRedit;
import com.example.redit.model.SubReditDto;
import com.example.redit.model.SubReditDto.SubReditDtoBuilder;
import com.example.redit.repository.SubReditRepository;
import static java.util.stream.Collectors.toList;
import lombok.AllArgsConstructor;
import static java.time.Instant.now;
@Service
@AllArgsConstructor
public class SubReditService {

	@Autowired
	private final SubReditRepository subReditRepository;
	@Autowired
	private final AuthService authService;
	
	@Transactional
	public List<SubReditDto> getAll(){
		return subReditRepository.findAll().stream().map(this::mapToDto).collect(toList());
	}
	
	@Transactional
	public SubReditDto save(SubReditDto subReditDto) throws Exception {
		SubRedit subRedit = subReditRepository.save(mapToSubRedit(subReditDto));
		subReditDto.setId(subRedit.getId());
		return subReditDto;
				
	}
	
	@Transactional(readOnly =true)
	public SubReditDto getSubRedit(Long id) throws Exception {
		SubRedit subRedit = subReditRepository.findById(id)
				.orElseThrow(() -> new  Exception("sub redit not found" + id)
				);
		return mapToDto(subRedit);
	}
	
	private SubReditDto mapToDto(SubRedit subRedit) {
		return SubReditDto.builder().name(subRedit.getName())
		.id(subRedit.getId())
		.postCount(subRedit.getPost().size())
		.build();
	}
	
	private SubRedit mapToSubRedit(SubReditDto subReditDto) throws Exception {
		return SubRedit.builder().name(subReditDto.getName()).description(subReditDto.getDescription())
				.user(authService.getCurrentUser())
				.createDate(now()).build();
		
				
	}
}
