package com.criticalsoftware;

import com.criticalsoftware.cards.entities.Cards_Session;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

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

}