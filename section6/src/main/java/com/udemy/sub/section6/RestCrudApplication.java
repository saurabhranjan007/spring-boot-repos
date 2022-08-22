package com.udemy.sub.section6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RestCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestCrudApplication.class, args);
	}

}
