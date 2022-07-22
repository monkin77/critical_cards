package com.criticalsoftware.resources;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.resources.SessionResource;
import com.criticalsoftware.entities.Cards_Session_Mock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class SessionResourceTest {
    private static Cards_Session cs;

    @BeforeAll
    public static void setup() {
        cs = new Cards_Session();
        cs.session_type = "retro";
        cs.id = 0L;
        Cards_Session_Mock.addSession(cs);

        SessionResource.setCardsSessionClass(Cards_Session_Mock.class);
    }

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
        given()
                .pathParams("id", cs.id)
                .when().redirects().follow(false)
                .get("/session/{id}")
                .then()
                .statusCode(302)
                .header("Location", endsWith("/" + cs.session_type + "/" + cs.id));
    }
}