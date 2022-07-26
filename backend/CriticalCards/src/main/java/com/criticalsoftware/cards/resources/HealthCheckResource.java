package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("health")
public class HealthCheckResource {
    @GET
    @Path("ping")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Pings the Backend")
    @APIResponse(responseCode = "200", description = "Success")
    public Response simplePing() {
        return Response.ok().build();
    }


    @GET
    @Path("ping/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Pings the Backend with an id", description = "Pings the backend with an id, expecting it to return a string with that same value.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", example = "{id: 2}"))
    public Response ping(String id) {
        return Response.ok().entity("{\"id\":\"" + id + "\"}").build();
    }

    @GET
    @Path("db")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Tests the connection to the database", description = "Tests the database, returning a JSON object containing the number of Cards Sessions, Retro Lanes and Retro Cards.")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", example = "{\n" +
                            "  \"Sessions\": \"13\",\n" +
                            "  \"RetroLanes\": \"14\",\n" +
                            "  \"RetroCards\": \"0\"\n" +
                            "}"))
            }
    )
    public Response db() {
        try {
            return Response.ok().entity("{" +
                    "\"Sessions\":\"" + Cards_Session.count() + "\"," +
                    "\"RetroLanes\":\"" + Retro_Lane.count() + "\"," +
                    "\"RetroCards\":\"" + Retro_Card.count() + "\"" +
                    "}").build();
        } catch (Exception ex) {
            return Response.ok("\"error\":" + ex.getMessage() + "}").build();
        }
    }

    @GET
    @Path("metrics")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Tests the time it takes to build the board's JSON responses", description = "Get metrics regarding the time it takes to build the board's JSON responses, to verify the database load")
    @APIResponses(
            value = {
                    @APIResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", example = "{\n" +
                            "  \"duration\": 31,\n" +
                            "  \"numSamples\": 10\n" +
                            "}")),
                    @APIResponse(responseCode = "204", description = "There are 0 sessions created, so it's not possible to build board's responses", content = @Content(mediaType = "application/json")),
                    @APIResponse(responseCode = "500", description = "Error fetching a retro from the randomly selected sessions", content = @Content(mediaType = "application/json", example = "{\n" +
                            "  \"error\": \"Error fetching Retro Resource with id: 28.\"\n" +
                            "}"))
            }
    )
    public Response metrics(@DefaultValue("10") @QueryParam("numberSamples") int numberSamples) {
        final List<Cards_Session> allSessions = Cards_Session.listAll();
        int numSessions = Integer.min(numberSamples, allSessions.size());
        if (numSessions == 0)
            return Response.status(204).build();

        List<Cards_Session> randomSessions = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numSessions; i++) {
            int chosenIdx = random.nextInt(allSessions.size());
            Cards_Session removedSession = allSessions.remove(chosenIdx);
            randomSessions.add(removedSession);
        }

        long start = System.currentTimeMillis();
        for (Cards_Session session : randomSessions) {
            Response res = new RetroResource().getRetro(session.id);
            if (res.getStatusInfo().getStatusCode() != 200)
                return Response.status(500).entity(Json.createObjectBuilder().add("error", "Error fetching Retro Resource with id: " + session.id + ".").build()).build();
        }
        long end = System.currentTimeMillis();

        long meanTime = (end - start) / numSessions;
        String body = Json.createObjectBuilder()
                .add("duration", meanTime)
                .add("numSamples", numSessions)
                .build().toString();
        return Response.ok(body).build();
    }
}
