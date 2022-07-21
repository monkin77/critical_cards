package com.criticalsoftware.cards.mappers;

import com.criticalsoftware.cards.dtos.RetroDTO;
import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Lane;

import java.util.List;

public class RetroMapper {
    public static RetroDTO getDTO(Cards_Session session) {
        if (!session.session_type.equals("retro"))
            return null;
        RetroDTO dto = new RetroDTO();
        dto.setId(session.id);
        dto.setName(session.session_name);
        List<Retro_Lane> lanes = Retro_Lane.findBySession(session.id);
        for (Retro_Lane lane : lanes) {
            dto.addLane(RetroLaneMapper.getDTO(lane));
        }
        return dto;
    }
}
