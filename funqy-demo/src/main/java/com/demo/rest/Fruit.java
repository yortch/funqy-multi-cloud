package com.demo.rest;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Fruit {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object anotherObject) {
        if (anotherObject != null && anotherObject instanceof Fruit) {
            Fruit anotherFruit = ((Fruit)anotherObject);
            if (anotherFruit.getName().equals(this.name)) {
                return true;
            }
        }
        return false;
    }

}