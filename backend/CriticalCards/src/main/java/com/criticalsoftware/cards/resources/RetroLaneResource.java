package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.Color;
import com.criticalsoftware.cards.dtos.SimpleCardDTO;
import com.criticalsoftware.cards.dtos.SimpleLaneDTO;
import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;
import com.criticalsoftware.cards.mappers.RetroMapper;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

@Path("retro/{sessionId}/lane")
public class RetroLaneResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createLane(@PathParam("sessionId") long sessionId, SimpleLaneDTO data) {
        Retro_Lane lane = new Retro_Lane();
        lane.cards_session_id = sessionId;
        lane.retro_lane_name = data.name;
        if (data.color != null) {
            lane.retro_lane_color = Color.webToInt(data.color);
        }
        lane.persist();
        URI uri = UriBuilder.fromPath("retro/" + sessionId).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{laneId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateLane(@PathParam("sessionId") long sessionId, @PathParam("laneId") long laneId, SimpleLaneDTO data) {
        Cards_Session retro = Cards_Session.getRetro(sessionId);
        Retro_Lane lane = Retro_Lane.getLane(sessionId, laneId);
        if (lane == null)
            Response.status(404).build();
        lane.retro_lane_name = data.name;
        if (data.color != null)
            lane.retro_lane_color = Color.webToInt(data.color);
        lane.persist();
        URI uri = UriBuilder.fromPath("retro/" + sessionId).build();
        String json = RetroMapper.getDTO(retro).toJSON();
        return Response.ok(uri).entity(json).build();
    }

    @DELETE
    @Path("{laneId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response removeLane(@PathParam("sessionId") long sessionId, @PathParam("laneId") long laneId) {
        Cards_Session retro = Cards_Session.getRetro(sessionId);
        if (!Retro_Lane.deleteById(laneId))
            Response.status(404).build();
        URI uri = UriBuilder.fromPath("retro/" + sessionId).build();
        String json = RetroMapper.getDTO(retro).toJSON();
        return Response.ok(uri).entity(json).build();
    }

    @POST
    @Path("{laneId}/card")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCardOnDefaultLane(@PathParam("sessionId") long sessionId,
                                            @PathParam("laneId") long laneId, SimpleCardDTO data) {
        Retro_Card card = new Retro_Card();
        card.retro_card_text = data.text;
        card.retro_card_color = Color.webToInt(data.color);
        card.retro_lane_id = laneId;
        card.persist();
        return ResourceHelper.getOkResponse(sessionId);
    }

    @PUT
    @Path("{laneId}/card/{cardId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateAndMoveCard(@PathParam("sessionId") long sessionId, @PathParam("laneId") long laneId,
                                      @PathParam("cardId") long cardId, SimpleCardDTO data) {
        Optional<Retro_Card> cardOpt = Retro_Card.findByIdOptional(cardId);
        if (!cardOpt.isPresent())
            return Response.status(404).build();
        Retro_Card card = cardOpt.get();
        if (data.text != null)
            card.retro_card_text = data.text;
        if (data.color != null)
            card.retro_card_color = Color.webToInt(data.color);
        card.retro_lane_id = laneId;
        card.persist();
        try {
            return ResourceHelper.getOkResponse(sessionId);
        } catch (NotFoundException ex) {
            return Response.status(405).build();
        }
    }
}
