package com.springboot.quizz.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuizzAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzAppApplication.class, args);
	}

}
