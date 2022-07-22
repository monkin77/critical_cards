package com.criticalsoftware.entities;

import com.criticalsoftware.cards.entities.Cards_Session;
import io.quarkus.arc.Priority;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.impl.GenerateBridge;
import io.quarkus.test.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mock
public class Cards_Session_Mock extends Cards_Session {
    private static Map<Long, Cards_Session> sessions = new HashMap<>();

    public static void addSession(Cards_Session cs) {
        sessions.put(cs.id, cs);
    }

    public static Optional<Cards_Session> findByIdOptional(Object id) {
        return Optional.ofNullable(sessions.get((Long) id));
    }
}
