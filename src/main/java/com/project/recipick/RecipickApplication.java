package com.project.recipick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.project.recipick", "repository"})
public class RecipickApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipickApplication.class, args);
	}

}
