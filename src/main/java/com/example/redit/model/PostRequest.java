package com.example.redit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {
	private Long id;
	private String postName;
	private String url;
	private String description;
	private String subReditName;


}
