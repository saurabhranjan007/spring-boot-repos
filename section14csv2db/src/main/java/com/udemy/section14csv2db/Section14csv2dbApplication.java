package com.udemy.section14csv2db;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing    // enabling "Spring Batching"
public class Section14csv2dbApplication {

	public static void main(String[] args) {
		SpringApplication.run(Section14csv2dbApplication.class, args);
	}

}
