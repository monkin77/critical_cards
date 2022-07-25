package com.criticalsoftware;

import com.criticalsoftware.cards.entities.Retro_Lane;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@QuarkusTest
public class RetroLaneTest {
    private static Retro_Lane retroLane;

    @BeforeAll
    @ActivateRequestContext
    @Transactional
    public static void setup() {
        retroLane = new Retro_Lane();
        retroLane.cards_session_id = 0L;
        retroLane.persist();
    }

    @Test
    public void testGetNonExistentLaneId() {
        Assertions.assertNull(Retro_Lane.getLane(retroLane.cards_session_id, -1));
    }

    @Test
    public void testGetLaneWithInvalidSessionId() {
        Assertions.assertNull(Retro_Lane.getLane(-1, retroLane.id));
    }

    @Test
    public void testGetValidLane() {
        Assertions.assertEquals(retroLane, Retro_Lane.getLane(retroLane.cards_session_id, retroLane.id));
    }

    @Test
    public void testGetValidLaneSession() {
        Assertions.assertEquals(retroLane.cards_session_id, Retro_Lane.getSessionOfLane(retroLane.id));
    }

    @Test
    public void testGetInvalidLaneSession() {
        try {
            Retro_Lane.getSessionOfLane(-1);
        } catch(NotFoundException nfe) {
            return;
        }

        Assertions.fail();
    }

    @AfterAll
    @ActivateRequestContext
    @Transactional
    public static void cleanup() {
        Retro_Lane.findByIdOptional(retroLane.id).get().delete();
    }
}