package com.springJPA.Spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJpaDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringJpaDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepoository studentRepoository){
		return args -> {
			Student priya = new Student("Priya", "Priya", "priyapriya@gmail.com", 22);
			studentRepoository.save(priya);
		};
	}

}
