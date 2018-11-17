package nl.han.dea.wouter.dao;

import nl.han.dea.wouter.dto.playlists.PlaylistResponseDTO;
import nl.han.dea.wouter.dto.playlists.PlaylistsResponseDTO;
import nl.han.dea.wouter.dto.tracks.TrackDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    public Connection connection = null;
    private String sql;

    public PlaylistDAO() {
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

    public PlaylistsResponseDTO getPlaylists() {
        ResultSet results;
        List<PlaylistResponseDTO> playlists = new ArrayList<>();

        try {
            sql = "SELECT * FROM playlist";
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();

            while (results.next()) {
                PlaylistResponseDTO playlistResponseDTO = new PlaylistResponseDTO();
                playlistResponseDTO.setId(results.getInt("playlistId"));
                playlistResponseDTO.setName(results.getString("playlistTitle"));
                playlistResponseDTO.setOwner(results.getBoolean("owner"));

                playlists.add(playlistResponseDTO);
            }
            PlaylistsResponseDTO playlistsResponseDTO = new PlaylistsResponseDTO();
            playlistsResponseDTO.setPlaylists(playlists);
            return playlistsResponseDTO;
        } catch (SQLException e) {
            System.out.println("Error executing statement: " + e);
            return null;
        }
    }

    public void setPlaylistName(int playlistId, String newName) {
        try {
            sql = "UPDATE playlist SET playlistTitle = '" + newName + "' WHERE playlistId = " + playlistId;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing statement: " + e);
        }
    }

    public void addPlaylist(String title, boolean isOwner) {
        try {
            sql = "INSERT INTO playlist (playlistTitle, duration, owner) VALUES ('" + title + "', 0, " + isOwner + ")";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(int playlistId) {
        try {
            String sql1 = "DELETE FROM trackInPlaylist WHERE playlistId = " + playlistId;
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            statement1.executeUpdate();

            sql = "DELETE FROM playlist WHERE playlistId = " + playlistId;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        try {
            sql = "DELETE FROM trackInPlaylist WHERE trackId = " + trackId + " AND playlistId = " + playlistId;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrackIntoPlaylist(int playlistID, TrackDTO trackDTO) {
        try {
            sql = "INSERT INTO trackInPlaylist(trackID, playlistID, offlineAvailable) VALUES (?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, trackDTO.getId());
            statement.setInt(2, playlistID);
            statement.setBoolean(3, trackDTO.isOfflineAvailable());

            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Kan track niet toevoegen aan playlist: " + e.getMessage());
        }
    }
}
