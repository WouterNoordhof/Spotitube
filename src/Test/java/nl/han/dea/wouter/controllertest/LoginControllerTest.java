package nl.han.dea.wouter.controllertest;

import nl.han.dea.wouter.controllers.LoginController;
import nl.han.dea.wouter.dto.login.LoginRequestDTO;
import nl.han.dea.wouter.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class LoginControllerTest {
    LoginController loginController;
    LoginRequestDTO loginRequestDTO;
    UserService userService = Mockito.mock(UserService.class);

    @Before
    public void setup() {
        loginController = new LoginController();
        loginController.setUserService(userService);

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Wouter");
        loginRequestDTO.setPassword("Test");
    }

    @Test
    public void testIfUserserviceAgreesReturnsOk() throws SQLException {
        //Setup
        Mockito.when(userService.authenticate(loginRequestDTO.getUser(), loginRequestDTO.getPassword())).thenReturn(true);

        //Test
        Response test = loginController.login(loginRequestDTO);
        Assert.assertEquals(200, test.getStatus());
    }

    @Test
    public void testIfUserserviceDoesNotAgreeReturnsNotOk() throws SQLException {
        //Setup
        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        //Test
        Response test = loginController.login(loginRequestDTO);
        Assert.assertEquals(401, test.getStatus());
    }
}


