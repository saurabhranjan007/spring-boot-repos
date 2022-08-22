package com.udemy.section20;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import vaccine.Vaccine;
import vaccine.VaccineController;
import vaccine.VaccineService;

import static org.mockito.Mockito.when;

@SpringBootTest
public class VaccineControllerTest {

    @Autowired
    VaccineController controller;

    @MockBean
    VaccineService service;

    @Test
    void testGetVaccines() {

        when(service.getVaccines())
                .thenReturn(Flux.just(new Vaccine("Pfizer"), new Vaccine("J&J"), new Vaccine("Covaxin")));
        StepVerifier.create(controller.getVaccines())
                .expectSubscription()
                .expectNextCount(3)
                .expectComplete()
                .verify();
    }
}
