package com.udemy.sub.section6;

import entities.Products;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

// NOTE: Unit Test vs Integration Test.
// Integration Test - The test cases that are defined here are not unit test cases but the integration one,
//			   		  meaning the test cases are mostly checking for the integration flow of the application rather than
//		 			  testing the individual units..
// Unit Test - "pure unit tests", tests the single component by mocking them out which means if we are testing a certain
//			   controller the call would not go to the controller itself but the mocked version of it. When we do unit tests
//			   we mock functions, classes and all, and then test for the expected values of functionalities.

@RunWith(SpringRunner.class)
@SpringBootTest
class RestCrudApplicationTests {

//	Instead of hardcoding the endpoints, now we can invoke a generic endpoint address.
//	And, inject its property, the way we do with .env in ReactJS or JS applications.

	@Value("${productrestapi.services.url}")
	private String baseURL;  // now instead of hardcoding endpoints we can concatenate this "baseURL"

//	testing the GET - method (to get the details about the products/product).
	@Test
	public void testGetProduct() {
		System.out.println("baseURL: "+baseURL);
//		using the "Rest Template" to retrieve a single product.
		RestTemplate restTemplate = new RestTemplate();
//		invoking the end point to GET(retrieve) a single product.. "Products.class" - here basically is the response type.
		Products getProduct = restTemplate.getForObject(baseURL+"1", Products.class);
		assertNotNull(getProduct); // testing to see if the "getProduct" assertion is null.
		assertEquals("Iphone", getProduct.getName()); // testing to check if the name of the product matches.
	}

//	testing the POST - method (to add new product in the database).
	@Test
	public void testPostMethod() {
		RestTemplate restTemplate = new RestTemplate();
		Products postProduct = new Products();
//		since this is a test case for POST, so setting the data fields with custom values.
		postProduct.setName("Samsung");
		postProduct.setDescription("Samsung series S smartphone..");
		postProduct.setPrice(1000);
		Products newProduct = restTemplate.postForObject(baseURL, postProduct, Products.class);
		assertNotNull(postProduct); // testing for Null Assertion.
		assertNotNull(postProduct.getId());  // testing for NUll ID.
		assertEquals("Samsung", postProduct.getName());  // testing to match the name of the product.
		assertEquals(1000, postProduct.getPrice());  // testing to match the price of the product.
	}

//	testing PUT - method (to update an existing product from the database).
	@Test
	public void testPutMethod() {
		RestTemplate restTemplate = new RestTemplate();
//		to change the product entry from the database using "PUT - method", we first get the product using ".getForObject()"..
//		and once we have the details update the entries and run ".put()" on "restTemplate" to update the product in database.
		Products putProduct = restTemplate.getForObject(baseURL+"1", Products.class);

		putProduct.setPrice(1500);
		restTemplate.put(baseURL, putProduct);
//		this specifies that we are updating the product entry at "id - 1" within this collection "/products/".

		assertNotNull(putProduct);  // checking for Null Assertion.
		assertEquals(1500, putProduct.getPrice());  // testing for the given price of the product in database.
	}
}
