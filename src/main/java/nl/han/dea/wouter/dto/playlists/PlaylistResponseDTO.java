package nl.han.dea.wouter.dto.playlists;

import nl.han.dea.wouter.dto.tracks.TracksDTO;

public class PlaylistResponseDTO {
    private int id;
    private String name;
    private boolean owner;
    private TracksDTO tracks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public TracksDTO getTracks() {
        return tracks;
    }

    public void setTracks(TracksDTO tracks) {
        this.tracks = tracks;
    }
}
