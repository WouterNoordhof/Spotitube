package nl.han.dea.wouter.controllers;

import nl.han.dea.wouter.dao.PlaylistDAO;
import nl.han.dea.wouter.dao.TrackDAO;
import nl.han.dea.wouter.dto.playlists.PlaylistResponseDTO;
import nl.han.dea.wouter.dto.playlists.PlaylistsResponseDTO;
import nl.han.dea.wouter.dto.tracks.TrackDTO;
import nl.han.dea.wouter.dto.tracks.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistsController {
    TrackDAO trackDAO;
    PlaylistDAO playlistDAO;

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token) {
        playlistDAO.deletePlaylist(playlistId);
        return Response.ok(playlistDAO.getPlaylists()).build();
    }

    @DELETE
    @Path("/{playlistId}/tracks/{trackId}")
    public Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {

        playlistDAO.deleteTrackFromPlaylist(playlistId, trackId);
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.setTracks(trackDAO.getTracksFromPlaylist(playlistId));

        return Response.ok(tracksDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        PlaylistsResponseDTO playlistsResponseDTO = playlistDAO.getPlaylists();
        return Response.ok(playlistsResponseDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/tracks")
    public Response getTracks(@PathParam("id") int playlistId) {
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.setTracks(trackDAO.getTracksFromPlaylist(playlistId));

        return Response.ok(tracksDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewPlaylist(PlaylistResponseDTO playlistResponseDTO, @QueryParam("token") String token) {
        if (token.equals("1234-1234-1234")) {
            playlistResponseDTO.setOwner(true);
        }

        playlistDAO.addPlaylist(playlistResponseDTO.getName(), playlistResponseDTO.isOwner());
        return Response.ok(playlistDAO).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}/tracks")
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String token, TrackDTO trackDTO) {
        playlistDAO.addTrackIntoPlaylist(playlistId, trackDTO);

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.setTracks(trackDAO.getTracksFromPlaylist(playlistId));
        return Response.ok(tracksDTO).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response changePlaylistName(PlaylistResponseDTO playlistResponseDTO, @PathParam("id") int playlistId, @QueryParam("token") String token) {
        playlistDAO.setPlaylistName(playlistId, playlistResponseDTO.getName());
        return Response.ok(playlistDAO).build();
    }
}
