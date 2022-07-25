package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Path("health")
public class HealthCheckResource {

    @GET
    @Path("ping")
    @Produces(MediaType.APPLICATION_JSON)
    public Response simplePing() {
        return Response.ok().build();
    }

    @GET
    @Path("ping/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping(String id) {
        return Response.ok().entity("{\"id\":\"" + id + "\"}").build();
    }

    @GET
    @Path("db")
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response metrics() {
        final List<Cards_Session> allSessions = Cards_Session.listAll();
        int numSessions = Integer.min(10, allSessions.size());
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
                return Response.status(500).entity("Error fetching Retro Resource with id: " + session.id + ".").build();
            System.out.println("Session: " + session.id + " elapsed time: " + (System.currentTimeMillis() - start));
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
