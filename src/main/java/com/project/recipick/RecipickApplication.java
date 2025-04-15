package com.project.recipick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.recipick.mapper")
public class RecipickApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipickApplication.class, args);
    
		System.out.println("push push");
	}
}
