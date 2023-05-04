package com.demo.rest;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * In memory implementation for managing fruits
 */
@ApplicationScoped
public class FruitService {

    private ArrayList<Fruit> fruitList;

    @PostConstruct
    private void init() {
        fruitList = new ArrayList<>();
    }

    public List<Fruit> findAll() {
        return fruitList;
    }

    public List<Fruit> add(Fruit fruit) {
        fruitList.add(fruit);
        return findAll();
    }

    public Fruit get(String name) {
        Fruit fruit = fruitList.stream()
            .filter(item -> name.equals(item.getName()))
            .findAny()
            .orElse(null);
        return fruit;
    }

    public Fruit remove(String name) {
        Fruit fruit = get(name);
        if (fruit != null) {
            fruitList.remove(fruit);
        }
        return fruit;
    }
}