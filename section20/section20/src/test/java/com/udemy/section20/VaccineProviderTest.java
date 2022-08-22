package com.udemy.section20;


// In other components we're doing straight "Junit" (essentially an integration) test.
// Here in this component we will do "Pure Unit Testing" using "Mockito"

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import vaccine.Vaccine;
import vaccine.VaccineProvider;
import vaccine.VaccineService;

import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class VaccineProviderTest {

    @Autowired
    VaccineProvider provider;

    @MockBean
    VaccineService service;
    // since we've marked this with MockBean, so while testing Spring will not make an actual
    // call to this "VaccineService" but in tern will "Mock" an instance of it & do the calls thro their

    @Test
    void testVaccineProvider() {
        // here we're going to Mock "VaccineService" so that while testing the request get hit to this
        // and not to the actual "VaccineService" defined in different Class.
        when(service.getVaccines())
                .thenReturn(Flux.just(new Vaccine("Pfizer"), new Vaccine("J&J"), new Vaccine("Covaxin")));
        // here it'll check if method call is ".getVaccine()", then it'll return the 'Flux' of mocked vaccines
        StepVerifier.create(provider.provideVaccines())
                .expectSubscription()
                .expectNext(new Vaccine("Pfizer"))
                .expectNext(new Vaccine("J&J"))
                .expectNext(new Vaccine("Covaxin"))
                .expectComplete()
                .verify();
        // so now when this provider gets invoked, it will inject the mocked "VaccineService - service"
        // and then the request will be further moved

        // Mockito also allows us to check if the calls that are defined are really happening using "verify".
        // As of now we've been doing the tests are if they passed then everything's good but Mockito
        // provides us with a way by which we can basically check if the mocking is actually happening or not

        // "verify" takes a parameter - which is the mocked one. And additionally we can invoke method that we're testing
        verify(service).getVaccines();
    }


}
