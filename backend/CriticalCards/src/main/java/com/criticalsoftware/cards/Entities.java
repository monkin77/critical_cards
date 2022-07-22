package com.criticalsoftware.cards;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;
import com.criticalsoftware.cards.entities_mock.Cards_Session_Mock;

public class Entities {
    public static Cards_Session cards_session = new Cards_Session();
    public static Retro_Card retro_card = new Retro_Card();
    public static Retro_Lane retro_lane = new Retro_Lane();

    public static void testMode() {
        cards_session = new Cards_Session_Mock();
        // TODO
    }
}
