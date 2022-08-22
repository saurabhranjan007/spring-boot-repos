package com.saurabh.section4.section4;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Greetings {
    
    @RequestMapping("/")
    public String greeting() {
        return "Hello.. good morning.";
    }

    @GetMapping("/new-page")
    public String newpage() {
        return "This is a new page.. ";
    }
}