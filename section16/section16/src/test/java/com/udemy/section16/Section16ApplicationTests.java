package com.udemy.section16;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Section16ApplicationTests {

	// To send message first let's "Autowire" it
	@Autowired
	MessageSender sender;

	// Defining test cases to test send and receive message
	@Test
	public void testSendAndReceive() {

		// Sending the message
		sender.send("Hello from Spring JSM sender..");
	}

}
