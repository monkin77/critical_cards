package com.criticalsoftware;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Lane;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CardsSessionTest {
    private static Cards_Session cs;
    private static Cards_Session csNonRetro;

    @BeforeAll
    @ActivateRequestContext
    @Transactional
    public static void setup() {
        // "normal" retro session
        cs = new Cards_Session();
        cs.session_type = "retro";
        cs.session_name = "test-retro";
        cs.persist();

        // "non-retro" retro session
        csNonRetro = new Cards_Session();
        csNonRetro.session_type = "non-retro";
        csNonRetro.session_name = "test-non-retro";
        csNonRetro.persist();
    }

    @Test
    public void testGetNonExistingRetro() {
        assertNull(Cards_Session.getRetro(-1));
    }

    @Test
    public void testGetNonRetroRetro() {
        assertTrue(Cards_Session.findByIdOptional(csNonRetro.id).isPresent());
        assertNull(Cards_Session.getRetro(csNonRetro.id));
    }

    @Test
    public void testGetExistingRetro() {
        assertEquals(Objects.requireNonNull(Cards_Session.getRetro(cs.id)).id, cs.id);
    }

    @Test
    public void testGetDefaultLaneNoSession() {
        try {
            // should throw NotFoundException
            Cards_Session.getDefaultLane(-1);
        } catch (NotFoundException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetDefaultLane() {
        try {
            Cards_Session.getDefaultLane(cs.id);
        } catch (NotFoundException e) {
            // should't throw => there's a default lane
            fail();
        }
    }

    @AfterAll
    @ActivateRequestContext
    @Transactional
    public static void cleanup() {
        Cards_Session.findByIdOptional(csNonRetro.id).get().delete();
        Cards_Session.findByIdOptional(cs.id).get().delete();
    }
}
