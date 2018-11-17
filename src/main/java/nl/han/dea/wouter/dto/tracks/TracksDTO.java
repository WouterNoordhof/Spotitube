package nl.han.dea.wouter.dto.tracks;

import java.util.ArrayList;
import java.util.List;

public class TracksDTO {
    private List<TrackDTO> tracks = new ArrayList<>();

    public TracksDTO() {

    }

    public TracksDTO(List<TrackDTO> tracks) {
        this.setTracks(tracks);
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }
}
