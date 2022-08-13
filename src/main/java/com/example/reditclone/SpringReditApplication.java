package com.example.reditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.redit.config.SecurityConfig;
import com.example.redit.config.WebConfig;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableAsync
@ComponentScan(basePackages="com.example.redit")
@EntityScan(value="com.example.redit.model")
@EnableJpaRepositories("com.example.redit.repository")
@CrossOrigin(origins = "localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SpringReditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReditApplication.class, args);
	}

}
