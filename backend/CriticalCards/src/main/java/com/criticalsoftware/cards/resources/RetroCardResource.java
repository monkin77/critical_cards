package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.Color;
import com.criticalsoftware.cards.dtos.SimpleCardDTO;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("card")
public class RetroCardResource {

    @PUT
    @Path("{cardId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateCard(@PathParam("cardId") long cardId, SimpleCardDTO data) {
        Optional<Retro_Card> cardOpt = Retro_Card.findByIdOptional(cardId);
        if (!cardOpt.isPresent())
            return Response.status(404).build();
        Retro_Card card = cardOpt.get();
        if (data.text != null)
            card.retro_card_text = data.text;
        if (data.color != null)
            card.retro_card_color = Color.webToInt(data.color);
        card.persist();
        try {
            return ResourceHelper.getOkResponse(Retro_Lane.getSessionOfLane(card.retro_lane_id));
        } catch (NotFoundException ex) {
            return Response.status(405).build();
        }
    }

}
