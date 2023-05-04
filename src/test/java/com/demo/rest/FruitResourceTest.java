package com.demo.rest;

import io.quarkus.test.junit.QuarkusTest;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class FruitResourceTest {

    @Inject
    Logger logger;

    @Inject
    ObjectMapper objectMapper;

    @Test
    public void testGetAllEndpoint() {
        given()
            .when().get("/fruits")
            .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

    @Test
    public void testAddEndpoint() {
        Fruit mango = new Fruit();
        mango.setName("Mango");
        mango.setDescription("Juicy");
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .body(getJson(mango))
            .when()
            .post("/fruits/")
            .then()
            .statusCode(200)
            .body("$.size()", is(1),
            "name", containsInAnyOrder(mango.getName()),
                "description", containsInAnyOrder(mango.getDescription()));
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .pathParam("name", mango.getName())
            .when()
            .delete("/fruits/{name}")
            .then()
            .statusCode(200);
    }

    @Test
    public void testGetEndpoint() {
        Fruit mango = new Fruit();
        mango.setName("Mango");
        mango.setDescription("Juicy");
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .body(getJson(mango))
            .when()
            .post("/fruits")
            .then()
            .statusCode(200);
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .pathParam("name", mango.getName())
            .when()
            .get("/fruits/{name}")
            .then()
            .statusCode(200)
            .body("name", equalTo(mango.getName()))
            .body("description", equalTo(mango.getDescription()));
        given()
            .header("Content-Type", MediaType.APPLICATION_JSON)
            .pathParam("name", mango.getName())
            .when()
            .delete("/fruits/{name}")
            .then()
            .statusCode(200);
    }

    private String getJson(Fruit fruit) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(fruit);
        } catch (Exception e) {
            logger.warn("Error serializing to json: " + e.getMessage(), e);
        }
        return json;
    }
}