package com.bridgelabz.contoller;

import com.bridgelabz.controller.UserController;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.RegistrationDto;
import com.bridgelabz.exception.BookException;
import com.bridgelabz.exception.UserException;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.response.Response;
import com.bridgelabz.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Autowired
    Environment environment;

    @InjectMocks
    private UserController userController;

    @Autowired
    UserModel userModel;

    @BeforeEach
    public void mockitoRule() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenRegistrationDto_WhenClickOnRegister_ShouldReturnTrue() throws UserException {
        RegistrationDto registrationDto=new RegistrationDto("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124");
        Mockito.when(userService.register(registrationDto)).thenReturn(true);
        ResponseEntity<Response> register = userController.register(registrationDto);
        String response="user register successful";
        Assert.assertEquals(register.getStatusCodeValue(),200);
    }
    @Test
    public void givenAlreadyRegisteredDto_WhenClickOnRegister_ShouldReturnFalse() throws UserException {
        RegistrationDto registrationDto2=new RegistrationDto("Thalari Yeshwanth", "yeshwanththalari0123@gmaiil.com", "9666924586", "154G5a0124");
        Mockito.when(userService.register(registrationDto2)).thenReturn(false);
        ResponseEntity<Response> response = userController.register(registrationDto2);
        Assert.assertEquals(response.getBody().getStatus(),400);
    }

    @Test
    public void givenLoginDto_WhenClickOnLogin_ShouldReturnToken() throws UserException {
        LoginDto loginDto=new LoginDto( "yeshwanththalari0123@gmaiil.com",  "154G5a0124");
        String token="asdfghjkl";
        Mockito.when(userService.login(loginDto)).thenReturn(token);
        ResponseEntity<Response> login = userController.login(loginDto);
        Assert.assertEquals(login.getStatusCodeValue(),200);
    }

    @Test
    public void givenInCorrectLoginDto_WhenClickOnLogin_ShouldReturnProperMessage() throws UserException {
        LoginDto loginDto=new LoginDto( "yeshwanththalari0123@gmaiil.com",  "154G5a0124");
        String token=null;
        Mockito.when(userService.login(loginDto)).thenReturn(token);
        ResponseEntity<Response> response = userController.login(loginDto);
        System.out.println(response);
        Assert.assertEquals(response.getBody().getStatus(),400);
    }

    @Test
    public void givenUserWithoutRegistration_WhenClickOnLogin_ShouldThrowProperException(){
        LoginDto loginDto=new LoginDto( "yeshwanththalari0123@gmaiil.com",  "154G5a0124");
        UserException expectedException;
        expectedException= new UserException("User not found",UserException.ExceptionType.INVALID_USER);
        try {
            when(userService.login(loginDto)).thenThrow(expectedException);
            userController.login(loginDto);
        } catch (UserException e) {
            Assert.assertEquals(UserException.ExceptionType.INVALID_USER,e.type);
        }
    }

}
