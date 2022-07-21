package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.entities.Retro_Card;
import com.criticalsoftware.cards.entities.Retro_Lane;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

}
