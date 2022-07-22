package com.criticalsoftware;

import com.criticalsoftware.cards.entities.Cards_Session;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class SessionResourceTest {
    private static Cards_Session cs;
    private static Cards_Session csNonExistent;

    @BeforeAll
    @ActivateRequestContext
    @Transactional
    public static void setup() {
        cs = new Cards_Session();
        cs.session_name = "test_session";
        cs.session_type = "retro";
        cs.persist();

        csNonExistent = new Cards_Session();
        cs.session_name = "not-null";
        cs.session_type = "poker";
        csNonExistent.persist();
        csNonExistent.delete();
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

    @AfterAll
    @ActivateRequestContext
    @Transactional
    public static void cleanup() {
        Cards_Session.findByIdOptional(cs.id).get().delete();
    }
}