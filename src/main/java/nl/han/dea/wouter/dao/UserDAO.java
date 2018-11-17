package nl.han.dea.wouter.dao;

import java.sql.*;

public class UserDAO {
    public Connection connection = null;

    public UserDAO() {
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

    public ResultSet makeQueryWithUser(String user) {
        ResultSet results;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE username = '" + user + "'");
            results = statement.executeQuery();

            return results;
        } catch (SQLException e) {
            System.out.println("Error executing statement: " + e);
            return null;
        }
    }

    public boolean authenticate(String user, String password) throws SQLException {
        ResultSet results = makeQueryWithUser(user);
        boolean authenticated = false;

        while (results.next()) {
            String db_username = results.getString("username");
            String db_password = results.getString("password");
            if (user.equals(db_username) && password.equals(db_password)) {
                authenticated = true;
            }
        }
        return authenticated;
    }
}
