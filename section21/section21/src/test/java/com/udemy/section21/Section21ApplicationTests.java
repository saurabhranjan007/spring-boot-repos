package com.udemy.section21;

import controller.ProductController;
import entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import repos.ProductRepository;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
class Section21ApplicationTests {

	@Autowired
	ProductController controller;

	@MockBean
	ProductRepository repo;
	// we're mocking this repository 'coz this is the one being used for "addProduct" & "getProduct"

	@Test
	void testAddProduct() {

		// Here the major thing that we need to do in order to test this method is mock a returning
		// product entry as well, so that the calls will not go to the database
		Product product = new Product(null, "Product101", "test product", 2003d);
		Product savedProduct = new Product("abc12", "Product101", "test product", 2003d);

		// Making the Mock calls, the returning "product" should be of type "Mono"
		when(repo.save(product)).thenReturn(Mono.just(savedProduct));

		// Initializing "StepVerifier"
		StepVerifier.create(controller.addProduct(product))
				// asserting to check certain conditions are met
				.assertNext(p-> {
					assertNotNull(p);
					assertNotNull(p.getId());
//					assertEquals("abc12", p.getId());
				})
				.expectComplete()
				.verify();

		// Checking if the Mocking has actually happened
		verify(repo).save(product);
	}

	// Testing "getProducts" method
	@Test
	public void testGetMethod() {
		when(repo.findAll())
				.thenReturn(Flux.just(
						new Product("abc12", "Product101", "test product", 2003d),
						new Product("abc123", "Product101", "test product", 2003d),
						new Product("abc1234", "Product101", "test product", 2003d)));

		// Kicking off the flow using "StepVerifier"
		StepVerifier.create(controller.getProducts())
				.expectSubscription()
				.expectNextCount(3)
				.expectComplete()
				.verify();

		// Mockito verify
		verify(repo).findAll();
	}

}
