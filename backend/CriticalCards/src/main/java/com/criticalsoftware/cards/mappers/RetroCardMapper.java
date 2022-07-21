package com.criticalsoftware.cards.mappers;

import com.criticalsoftware.cards.dtos.RetroCardDTO;
import com.criticalsoftware.cards.entities.Retro_Card;

public class RetroCardMapper {
    public static RetroCardDTO getDTO(Retro_Card card) {
        RetroCardDTO dto = new RetroCardDTO();
        dto.setId(card.id);
        dto.setRetro_card_text(card.retro_card_text);
        dto.setRetro_card_color(card.retro_card_color);
        dto.setRetro_votes(card.retro_votes);
        dto.setRetro_lane_id(card.retro_lane_id);
        return dto;
    }
}
