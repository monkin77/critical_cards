package com.criticalsoftware.cards.mappers;

import com.criticalsoftware.cards.dtos.RetroLaneDTO;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;

public class RetroLaneMapper {
    public static RetroLaneDTO getDTO(Retro_Lane lane) {
        RetroLaneDTO dto = new RetroLaneDTO();
        dto.setId(lane.id);
        dto.setRetro_lane_name(lane.retro_lane_name);
        dto.setRetro_lane_color(lane.retro_lane_color);
        dto.setCards_session_id(lane.cards_session_id);
        for (Retro_Card card : Retro_Card.findByLane(lane.id)) {
            dto.addCard(RetroCardMapper.getDTO(card));
        }
        return dto;
    }
}
