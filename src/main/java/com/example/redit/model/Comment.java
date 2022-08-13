package com.example.redit.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long id;
	
	@NotEmpty
	private String text;
	
	@ManyToOne
	@JoinColumn(name ="postId", referencedColumnName ="postId")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name ="userId", referencedColumnName ="userId")
	private User user;

	private Instant createdDate;
}
