package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.Color;
import com.criticalsoftware.cards.dtos.SimpleCardDTO;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
    @Operation(summary = "Updates a Card", description = "Changes a Card's text and color if the received properties are not null")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Success"),
                    @APIResponse(responseCode = "404", description = "Unable to find the card with the given cardId"),
                    @APIResponse(responseCode = "405")
            }
    )
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

    @PUT
    @Path("vote/{cardId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Adds a vote to a Card", description = "Increments the number of votes in a card by 1")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", example = "{votes: 4}")),
                    @APIResponse(responseCode = "404", description = "Unable to find the card with the given cardId"),
                    @APIResponse(responseCode = "405")
            }
    )
    public Response addVote(@PathParam("cardId") long cardId){
        Optional<Retro_Card> cardOpt = Retro_Card.findByIdOptional(cardId);
        if (!cardOpt.isPresent())
            return Response.status(404).build();
        Retro_Card card = cardOpt.get();
        card.retro_votes++;
        card.persist();
        try {
            return Response.ok().entity("{\"votes\":\"" + card.retro_votes + "\"}").build();
        } catch (NotFoundException ex) {
            return Response.status(405).build();
        }
    }

    @PUT
    @Path("unvote/{cardId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Removes a vote from a Card", description = "Decrements the number of votes in a card by 1 if > 0")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", example = "{votes: 3}")),
                    @APIResponse(responseCode = "404", description = "Unable to find the card with the given cardId"),
                    @APIResponse(responseCode = "405")
            }
    )
    public Response removeVote(@PathParam("cardId") long cardId ){
        Optional<Retro_Card> cardOpt = Retro_Card.findByIdOptional(cardId);
        if (!cardOpt.isPresent())
            return Response.status(404).build();
        // metodo para deserializar o json
        Retro_Card card = cardOpt.get();
        if (card.retro_votes > 0 )
            card.retro_votes--;
        card.persist();
        try {
            return Response.ok().entity("{\"votes\":\"" + card.retro_votes + "\"}").build();
        } catch (NotFoundException ex) {
            return Response.status(405).build();
        }
    }
}
