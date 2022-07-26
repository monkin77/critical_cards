package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.Color;
import com.criticalsoftware.cards.dtos.SimpleCardDTO;
import com.criticalsoftware.cards.dtos.SimpleSessionDTO;
import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.mappers.RetroMapper;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

@Path("retro")
public class RetroResource {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetro(long id) {
        Optional<Cards_Session> session = Cards_Session.findByIdOptional(id);
        if (!session.isPresent())
            return Response.status(404).build();
        String json = RetroMapper.getDTO(session.get()).toJSON();
        return Response.ok(json).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createRetro(SimpleSessionDTO session) {
        Cards_Session retro = new Cards_Session();
        retro.session_type = "retro";
        retro.session_name = session.name;
        retro.persist();
        URI uri = UriBuilder.fromPath("retro/" + retro.id).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateRetro(long id, SimpleSessionDTO data) {
        Optional<Cards_Session> session = Cards_Session.findByIdOptional(id);
        if (!session.isPresent())
            return Response.status(404).build();
        session.get().session_name = data.name;
        session.get().persist();
        URI uri = UriBuilder.fromPath("retro/" + session.get().id).build();
        String json = RetroMapper.getDTO(session.get()).toJSON();
        return Response.ok(uri).entity(json).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteRetro(long id) {
        if (Cards_Session.deleteById(id)) {
            return Response.status(204).build();
        }
        return Response.status(404).build();
    }

    @POST
    @Path("{sessionId}/card")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCardOnDefaultLane(@PathParam("sessionId") long sessionId, SimpleCardDTO data) {
        long laneId;
        try {
            laneId = Cards_Session.getDefaultLane(sessionId);
        } catch (NotFoundException ex) {
            return Response.status(405).build();
        }
        Retro_Card card = new Retro_Card();
        card.retro_card_text = data.text;
        if(data.color != null)
            card.retro_card_color = Color.webToInt(data.color);
        card.retro_lane_id = laneId;
        card.persist();
        return ResourceHelper.getOkResponse(sessionId);
    }

}
