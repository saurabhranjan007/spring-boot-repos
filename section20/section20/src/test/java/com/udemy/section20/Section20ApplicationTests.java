package com.udemy.section20;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import vaccine.Vaccine;
import vaccine.VaccineConsumer;
import vaccine.VaccineProvider;

import java.time.Duration;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class Section20ApplicationTests {

	@Autowired
	VaccineProvider provider;

	// testing vaccine provider
	@Test
	void testVaccineProvider() {
		provider.provideVaccines().subscribe(new VaccineConsumer());
	}

	// creating new test cases using Reactive
	@Test
	void testVaccineProvider_reactive() {
		StepVerifier
				.create(provider.provideVaccines())
				// "create" takes the published and creates a step, on which we can set the expectations
				.expectSubscription()
				// subscription will happen automatically because of "StepVerifier"
				.expectNext(new Vaccine("Pfizer"))
				// we expect the Next because it is a Flux, and it will have multiple element
				// here we're expecting Next signal with the defined data "new Vaccine()"
				.expectNext(new Vaccine("J&J"))
				// expecting three signals since it has three elements
				.expectNext(new Vaccine("Covaxin"))
				.expectComplete()
				.verify();
				// "verify()" - will be invoked for the entire process to be kicked off
	}

	// test case to explore "expectNextCount" - method
	@Test
	void testVaccineProvider_reactive_expectNextCount() {
		StepVerifier
				.create(provider.provideVaccines())
				.expectSubscription()
//				.expectNext(new Vaccine("Pfizer"))
//				.expectNext(new Vaccine("J&J"))
//				.expectNext(new Vaccine("Covaxin"))
				.expectNextCount(3)
				// here instead of verifying for all the 3 elements by name we're
				// verifying the number of requests that are being sent and received
				.expectComplete()
				.verify();
		// Note - as you can see here we're checking for the 3 elements that are being
		//	 	  published and consumed. If we verify these number of elements then
		//	 	  things wouldn't be as hectic. That's where ".expectNextCount()" comes in
	}

	// test case for "assertNext" and "assertThat" method
	// adding "expectNextMatches" - here as well.
	@Test
	void testVaccineProvider_reactive_assertThat() {
		StepVerifier
				.create(provider.provideVaccines())
				.expectSubscription()
				.assertNext(vaccine -> {
					assertThat(vaccine.getName()).isNotNull();
					assertTrue(vaccine.isDelivered());
					asserEquals("Pfizer",vaccine.getName());
				})
				.expectNextMatches(vaccine -> {
//					assertThat(vaccine.getName()).isNotNull();
//					assertTrue(vaccine.isDelivered());
//					asserEquals("Pfizer",vaccine.getName());
					// We can use for these "assert" statements as well but the return type here is boolean
					// and if we're expecting much more information than just true/false, then it's not so good to use.
					return true;
				})
				.expectNext(new Vaccine("J&J"))
				.expectNext(new Vaccine("Covaxin"))
				.expectComplete()
				.verify();
//		Note: as you can see here we're doing "expectNext" to check for the expected element
//			  There are certain number of "expectNext" we can do, but using "assert" we can handle
//			  as many conditions as we want, and also we can handle the type of element as well.
	}


	// Exploring "Mono"
	@Test
	void testMono() {
		Mono<String> mono = Mono.just("Macbook pro");
		// created a "Mono" of Object String, so it can work with any Object type
		mono.log().map(data->data.toUpperCase()).subscribe(data->System.out.println("Data: "+data));
		// this will log all the internal events within the mono to standard out automatically
		// this work return the instance of a mono since this is a mono instance
		// Note: for the data flow to kick in, add ".subscribe()" first
	}

	// Exploring "Flux"
	@Test
	void tesFlux() throws InterruptedException {
//		Flux<String> flux = Flux.just("Macbook Pro", "Iphone", "Google Pixel 6", "Samsung");

//		flux.delayElements(Duration.ofSeconds(2))
//				.log()
//				.map(data -> data.toUpperCase())
//				.subscribe(new OrderConsumer);

		// we can also create flux from an array or array list
		Flux.fromIterable(Arrays.asList("Macbook Pro", "Iphone", "Google Pixel 6", "Samsung", "Macbook Pro", "Iphone", "Google Pixel 6", "Samsung", "Dell"))
				.delayElements(Duration.ofSeconds(2))
				.log()
				.map(data -> data.toUpperCase())
				.subscribe(new Subscriber<String>() {

					// Implementing Batching..
					private long count=0;

					// defining "Subscription" here so that we can invoke it from any other method
					private Subscription subscription;

					@Override
					public void onSubscribe(Subscription subscription) {

						// invoking the subscription from above defined method
						this.subscription = subscription;
						subscription.request(3);
//						subscription.request(Long.MAX_VALUE);
						// this defines the no. of request this method is ready to handle
						// we can also define it to handle maximum value using "Long.MAX_VALUE"
						// Note: if we n=don't specify this teh data flow will not go through
					}

					@Override
					public void onNext(String order) {

						// implementing the batching logic here, and the publisher will publish two elements
						count++;  // incrementing the count everytime batching is processed
						if(count >= 3) {
							count = 0;
							subscription.request(3);
							// here once the count reaches "0", we signal to start the batching again
							// using "subscription.request()". So publisher checks this and then publishes
							// the requested number of elements again
						}

						// here we invoke for each method published
						System.out.println(order);
					}

					@Override
					public void onError(Throwable error) {
						// printing stack trace for onError
						error.printStackTrace();

					}

					@Override
					public void onComplete() {
						// printing a generic method on complete
						System.out.println("Completely Done!!!!");
					}
				});
				// ".subscribe()" - method is overloaded to take different types of consumers or subscribers
				// we can specify the proper arguments to this ".subscribe()" method

		Thread.sleep(6000);
		// 'coz' of blocking nature of "flux" it will not print the output unless we add this
		// even tho we have implemented the ".delayElements()" method
	}

}
