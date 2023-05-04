package com.demo.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    FruitService service;

    @GET
    @Path("{name}")
    public Fruit getSingle(@PathParam("name") String name) {
        return service.get(name);
    }

    @GET
    public List<Fruit> getAllFruits() {
        return service.findAll();
    }

    @POST
    public List<Fruit> add(Fruit fruit) {
        return service.add(fruit);
    }

    @DELETE
    @Path("{name}")
    public Fruit remove(@PathParam("name") String name) {
        return service.remove(name);
    }

}