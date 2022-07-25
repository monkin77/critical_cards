package com.criticalsoftware.cards.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Retro_Lane extends PanacheEntity {
    public long cards_session_id;
    public String retro_lane_name;
    public int retro_lane_color;

    public static List<Retro_Lane> findBySession(long id) {
        return find("cards_session_id", id).list();
    }

    public static Retro_Lane getLane(long session, long id) {
        Optional<Retro_Lane> laneOpt = Retro_Lane.findByIdOptional(id);
        if (!laneOpt.isPresent())
            return null;
        Retro_Lane lane = laneOpt.get();
        if (lane.cards_session_id != session)
            return null;
        return lane;
    }

    public static long getSessionOfLane(long laneId) {
        Optional<Retro_Lane> laneOpt = Retro_Lane.findByIdOptional(laneId);
        if (laneOpt.isPresent())
            return laneOpt.get().cards_session_id;
        throw new NotFoundException();
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;

        Retro_Lane lane2 = (Retro_Lane) obj;

        return this.cards_session_id == lane2.cards_session_id &&
                Objects.equals(this.retro_lane_name, lane2.retro_lane_name) &&
                this.retro_lane_color == lane2.retro_lane_color &&
                this.id.equals(lane2.id);
    }
}
