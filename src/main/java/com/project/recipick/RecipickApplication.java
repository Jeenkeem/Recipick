package com.project.recipick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
<<<<<<< HEAD
@MapperScan("com.project.recipick.mapper")
@EnableScheduling
=======
>>>>>>> 4bde1dac5f6c1063e700682a5ea937fbe7b65427
public class RecipickApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipickApplication.class, args);
	}
}
