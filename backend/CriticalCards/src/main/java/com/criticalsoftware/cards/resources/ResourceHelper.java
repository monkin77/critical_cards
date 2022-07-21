package com.criticalsoftware.cards.resources;

import com.criticalsoftware.cards.entities.Cards_Session;
import com.criticalsoftware.cards.mappers.RetroMapper;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class ResourceHelper {

    protected static Response getOkResponse(long sessionId) {
        Cards_Session retro = Cards_Session.getRetro(sessionId);
        URI uri = UriBuilder.fromPath("retro/" + sessionId).build();
        String json = RetroMapper.getDTO(retro).toJSON();
        return Response.ok(uri).entity(json).build();
    }
}
