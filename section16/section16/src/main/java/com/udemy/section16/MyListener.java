package com.udemy.section16;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

// Creating this listener class, which wil listen at the defined destination and read the message
@Component
public class MyListener {

    // Here this "JmsListener()" annotation will allow this method to listen to the message
    // at the defined destination. We pass the destination as a param to the annotation
    @JmsListener(destination = "${springjms.myQueue}")
    public void receive(String message) {
        System.out.println("Message received: "+message);
    }
}

// Note: as soon as the application is up, the above defined listener will start listening.
//       And whenever sender sends a message Spring will automatically take the message and invoke
//       the "receive" method with "JmsListener" annotation.
