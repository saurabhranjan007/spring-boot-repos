package com.saurabh.section4.section4;

import PaymentService.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class Section4ApplicationTests {
//	@Test
//	void contextLoads() {
//	} -- this is a default test.

	@Autowired
	PaymentService service;

	@Test
	void testDependencyInjection() {
//		to assert null if the dependency injection happens as expected.
		assertNotNull(service);
	}

}
