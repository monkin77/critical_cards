package com.criticalsoftware;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class SessionResourceTest {

    @Test
    public void testGetNonExistentSession() {
        given()
                .pathParams("id", -1)
                .when().get("/session/{id}")
                .then()
                .statusCode(404)
                .body(is(""));
    }

    @Test
    public void testGetExistentSession() {
        long id = 0;
        given()
                .pathParams("id", id)
                .when().get("/session/{id}")
                .then()
                .statusCode(200)
                .body(containsString("\"id\":0,"));
    }
}