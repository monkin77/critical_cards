package com.criticalsoftware;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RetroCardsVotesTest {
    private static Retro_Lane rl;

    @BeforeAll
    @ActivateRequestContext
    @Transactional
    public static void setup() {
        rl = new Retro_Lane();
        rl.cards_session_id = 0L;
        rl.persist();
    }

    @Test
    public void testNegativeVotes() {
        List<Retro_Card> cards = Retro_Card.findByLane(rl.id);
        for (Retro_Card card : cards) {
            try {
                Assertions.assertTrue(card.retro_votes >= 0);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @AfterAll
    @ActivateRequestContext
    @Transactional
    public static void cleanup() {
        Retro_Lane.findByIdOptional(rl.id).get().delete();
    }
}