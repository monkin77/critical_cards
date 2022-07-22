package com.criticalsoftware;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CardsSessionTest {
    /*
    private static Cards_Session cs;
    private static Cards_Session csNonRetro;

    private static Retro_Lane rl;

    @BeforeAll
    public static void setup() {
        // "normal" retro session
        cs = new Cards_Session();
        cs.session_type = "retro";
        cs.id = 0L;
        Cards_Session_Mock.addSession(cs);

        // "non-retro" retro session
        csNonRetro = new Cards_Session();
        csNonRetro.session_type = "non-retro";
        csNonRetro.id = 1L;
        Cards_Session_Mock.addSession(csNonRetro);

        // default lane of "normal" retro session
        rl = new Retro_Lane();
        rl.id = 2L;
        rl.cards_session_id = cs.id;
        rl.retro_lane_name = null;
        Retro_Lane_Mock.setLanes(Collections.singletonList(rl));

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

    @Test
    public void testGetDefaultLaneNoSession() {
        try {
            // should throw NotFoundException
            Cards_Session_Mock.getDefaultLane(-1);
        } catch (NotFoundException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetDefaultLane() {
        assertEquals(Cards_Session_Mock.getDefaultLane(cs.id), rl.id);
    }
     */
}
