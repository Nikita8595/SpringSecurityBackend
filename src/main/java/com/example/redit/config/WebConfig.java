package com.example.redit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages ="com.example.redit.controller")
@CrossOrigin(origins = "localhost:4200")
public class WebConfig implements WebMvcConfigurer {

	@Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
	 corsRegistry.addMapping("/**")
     .allowedOrigins("*")
     .allowedMethods("*")
     .maxAge(3600L)
     .allowedHeaders("*")
     .exposedHeaders("Authorization")
     .allowCredentials(false);
	}
}
