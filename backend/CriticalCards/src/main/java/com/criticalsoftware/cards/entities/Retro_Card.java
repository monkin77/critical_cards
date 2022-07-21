package com.criticalsoftware.cards.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Entity
public class Retro_Card extends PanacheEntity {
    public long retro_lane_id;
    public String retro_card_text;
    public int retro_card_color;
    public int retro_votes;

    public static List<Retro_Card> findByLane(Long id) {
        return find("retro_lane_id", id).list();
    }

    public Cards_Session getRetro(long session) {
        Optional<Cards_Session> retroOpt = Cards_Session.findByIdOptional(session);
        if (!retroOpt.isPresent())
            return null;
        Cards_Session retro = retroOpt.get();
        if (!retro.session_type.equals("retro"))
            return null;
        return retro;
    }
}
