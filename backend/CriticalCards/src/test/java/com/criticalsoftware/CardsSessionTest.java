package com.criticalsoftware;

import com.criticalsoftware.cards.Entities;
import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities_mock.Cards_Session_Mock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CardsSessionTest {
    private static Cards_Session cs;
    private static Cards_Session csNonRetro;

    @BeforeAll
    public static void setup() {
        cs = new Cards_Session();
        cs.session_type = "retro";
        cs.id = 0L;
        Cards_Session_Mock.addSession(cs);

        csNonRetro = new Cards_Session();
        csNonRetro.session_type = "non-retro";
        csNonRetro.id = 1L;
        Cards_Session_Mock.addSession(csNonRetro);

        Entities.testMode();
    }

    @Test
    public void testGetNonExistingRetro() {
        assertNull(Cards_Session_Mock.getRetro(-1));
    }

    @Test
    public void testGetNonRetroRetro() {
        assertTrue(Cards_Session_Mock.findByIdOptional(csNonRetro.id).isPresent());
        assertNull(Cards_Session_Mock.getRetro(csNonRetro.id));
    }

    @Test
    public void testGetExistingRetro() {
        assertEquals(Objects.requireNonNull(Cards_Session_Mock.getRetro(cs.id)).id, cs.id);
    }
}
