package com.udemy.section16;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    // To send a message we need JMS Template
    @Autowired
    private JmsTemplate jmsTemplate;

    // Injecting the Queue, here instead of hardcoding let's pass it as value in application property
    @Value("${springjms.myQueue}")
    private String queue;

    // Creating send method
    public void send(String message) {
        System.out.println("Message sent: "+message);
        jmsTemplate.convertAndSend(queue, message);
        // this method will take an argument and convert it into an appropriate message
        // params to be passed - 1. destination, 2. data
    }
}
