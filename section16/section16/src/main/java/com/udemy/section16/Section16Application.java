package com.udemy.section16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class Section16Application {

	public static void main(String[] args) {
		SpringApplication.run(Section16Application.class, args);
	}

}

// Note: Messaging allows heterogeneous integration meaning applications build on different programming
//		  languages can communicate with each other seamlessly