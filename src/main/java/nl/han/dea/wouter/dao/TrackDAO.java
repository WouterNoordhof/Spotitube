package nl.han.dea.wouter.dao;

import nl.han.dea.wouter.dto.tracks.TrackDTO;
import nl.han.dea.wouter.dto.tracks.TracksDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {
    public Connection connection = null;
    private String sql;

    public TrackDAO() {
        makeConnection();
    }

    private void makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("JDBC:mysql://localhost:3306/Spotitube", "root", "");
        } catch (SQLException e) {
            System.out.println("Error connecting to a database" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<TrackDTO> getTracksFromPlaylist(int id) {
        List<TrackDTO> tracks = new ArrayList<>();
        try {
            sql = "SELECT * FROM tracks t INNER JOIN trackInPlaylist tp on t.trackId = tp.trackId WHERE tp.playlistId = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                TrackDTO trackDTO = fillTrackDtoExceptOfflineAvailable(results);
                trackDTO.setOfflineAvailable(results.getBoolean("offlineAvailable"));
                tracks.add(trackDTO);
            }
        } catch (Exception e) {
            System.out.println("Kan tracks van playlist niet krijgen: " + e.getMessage());
        }
        return tracks;
    }

    private TrackDTO fillTrackDtoExceptOfflineAvailable(ResultSet results) throws SQLException {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(results.getInt("trackId"));
        trackDTO.setTitle(results.getString("title"));
        trackDTO.setPerformer(results.getString("performer"));
        trackDTO.setDuration(results.getInt("duration"));
        trackDTO.setAlbum(results.getString("album"));
        trackDTO.setPlaycount(results.getInt("playcount"));
        trackDTO.setPublicationDate(results.getString("publicationDate"));
        trackDTO.setDescription(results.getString("description"));

        return trackDTO;
    }

    public TracksDTO showAllTracksNotInPlaylist(int playlistId) {
        List<TrackDTO> tracks = new ArrayList<>();

        try {
            sql = "SELECT * FROM tracks t WHERE NOT EXISTS(SELECT * FROM trackInPlaylist tp WHERE tp.trackId = t.trackId AND tp.playlistId = ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistId);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                TrackDTO trackDTO = fillTrackDtoExceptOfflineAvailable(results);
                tracks.add(trackDTO);
            }

            return new TracksDTO(tracks);
        } catch (Exception e) {
            System.out.println("Kan tracks behalve in playlist niet krijgen: " + e.getMessage());
        }
        return new TracksDTO(tracks);
    }
}
