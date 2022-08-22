package com.udemy.section14;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing    // enabling "Spring Batching"
public class Section14Application {

	public static void main(String[] args) {
		SpringApplication.run(Section14Application.class, args);
	}

}
