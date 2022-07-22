package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.entities.Cards_Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Optional;

@Path("/session")
public class SessionResource {
    private static Class<? extends Cards_Session> cs = Cards_Session.class;

    public static void setCardsSessionClass(Class<? extends Cards_Session> cs) {
        SessionResource.cs = cs;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionById(long id) {
        Optional<Cards_Session> session;
        try {
            Method m = cs.getMethod("findByIdOptional", Object.class);
            session = (Optional<Cards_Session>) m.invoke(null, id);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (!session.isPresent())
            return Response.status(404).build();
        URI uri = UriBuilder.fromPath(session.get().session_type + "/" + id).build();
        return Response.status(302).location(uri).build();
    }

}
