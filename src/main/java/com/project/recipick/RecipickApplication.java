package com.project.recipick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.project.recipick.mapper")
@EnableScheduling
public class RecipickApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipickApplication.class, args);
	}
}
