package com.example.redit.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;

import javax.persistence.Entity;

import static javax.persistence.FetchType.LAZY;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Setter
@Builder
public class Vote {
	
	@Id
	@GeneratedValue(strategy= SEQUENCE)
	private Long voteId;
	
	@NotNull
	@ManyToOne(fetch =LAZY)
	@JoinColumn(name ="postId", referencedColumnName ="postId")
	private Post post;
	
	@NotNull
	@ManyToOne(fetch =LAZY)
	@JoinColumn(name ="userId", referencedColumnName ="userId")
	private User user;
	
	private VoteType voteType;
	

}
