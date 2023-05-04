package com.demo.funqy;

import io.quarkus.funqy.Funq;

import jakarta.inject.Inject;

public class GreetingFunction {

    @Inject
    GreetingService service;

    @Funq
    public Greeting greet(Friend friend) {
        Greeting greeting = new Greeting();
        greeting.setMessage(service.greet(friend.getName()));
        return greeting;
    }
}
