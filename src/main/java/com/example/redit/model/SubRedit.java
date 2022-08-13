package com.example.redit.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.LAZY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class SubRedit {
	
	@Id
	@GeneratedValue(strategy =SEQUENCE)
	private Long id;
	
	@NotBlank(message ="Community name is required")
	private String name;
	
	@NotBlank(message ="Community name is required")
	private String description;

	@OneToMany(fetch =LAZY)
	private List<Post> post; 
	
	private Instant createDate;
	
	@ManyToOne(fetch =LAZY)
	private User user;  // one user can have many subredit therefor one user many subredit
	
	
		
	

}
