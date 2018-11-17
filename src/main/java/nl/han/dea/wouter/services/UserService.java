package nl.han.dea.wouter.services;

import nl.han.dea.wouter.dao.UserDAO;

import javax.inject.Inject;
import java.sql.SQLException;

public class UserService {

    @Inject
    UserDAO userDAO;

    public boolean authenticate(String user, String password) throws SQLException {
        boolean authenticated = userDAO.authenticate(user, password);
        return authenticated;
    }
}
