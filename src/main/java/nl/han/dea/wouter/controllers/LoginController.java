package nl.han.dea.wouter.controllers;

import nl.han.dea.wouter.dto.login.LoginRequestDTO;
import nl.han.dea.wouter.dto.login.LoginResponseDTO;
import nl.han.dea.wouter.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/login")
public class LoginController {
    private UserService userService;
// Test to see if Git works Properly
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) throws SQLException {
        if (userService.authenticate(loginRequestDTO.getUser(), loginRequestDTO.getPassword())) {
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setUser("Wouter");
            loginResponseDTO.setToken("1234-1234-1234");

            return Response.ok(loginResponseDTO).build();
        } else {
            return Response.status(401).build();
        }
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
