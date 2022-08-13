package com.example.redit.model;



import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.LAZY;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = SEQUENCE)
	private Long postId;
	
	@NotBlank(message ="post cannot be empty")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;
	
	private Integer voteCount;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name ="userId" , referencedColumnName = "userId")
	private User user;
	
	private Instant createdDate;
	
	@ManyToOne
	@JoinColumn(name ="id" , referencedColumnName ="id")
	 private SubRedit subRedit; //Same subredit can have many post that is why many to one (one Subredit many post )
	
	
	
	

}
