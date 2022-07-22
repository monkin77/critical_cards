package com.criticalsoftware.cards.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Entity
public class Cards_Session extends PanacheEntity {
    public String session_type;
    public String session_name;

    public static long getDefaultLane(long retro) {
        List<Retro_Lane> laneList =
                Retro_Lane.findBySession(retro);
        if (laneList.size() == 0)
            throw new NotFoundException();
        for (Retro_Lane retro_lane : laneList) {
            if (retro_lane.retro_lane_name == null)
                return retro_lane.id;
        }
        throw new NotFoundException();
    }

    public static Cards_Session getRetro(long session) {
        Optional<Cards_Session> retroOpt = Cards_Session.findByIdOptional(session);
        if (!retroOpt.isPresent())
            return null;
        Cards_Session retro = retroOpt.get();
        if (!retro.session_type.equals("retro"))
            return null;
        return retro;
    }
}
