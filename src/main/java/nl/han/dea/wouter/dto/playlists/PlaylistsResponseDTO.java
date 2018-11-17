package nl.han.dea.wouter.dto.playlists;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsResponseDTO {

    private List<PlaylistResponseDTO> playlists = new ArrayList<>();
    private int length;

    public List<PlaylistResponseDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistResponseDTO> playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void addToPlaylists(PlaylistResponseDTO playlist) {
        playlists.add(playlist);
    }
}
