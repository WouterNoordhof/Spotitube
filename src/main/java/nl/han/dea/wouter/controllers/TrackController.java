package nl.han.dea.wouter.controllers;

import nl.han.dea.wouter.dao.TrackDAO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackController {
    @Inject
    TrackDAO trackDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAllTracks(@QueryParam("forPlaylist") int forPlaylist, @QueryParam("token") String token) {
        return Response.ok(trackDAO.showAllTracksNotInPlaylist(forPlaylist)).build();
    }
}
