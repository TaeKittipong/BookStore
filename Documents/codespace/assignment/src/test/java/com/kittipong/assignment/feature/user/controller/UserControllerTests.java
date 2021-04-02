package com.kittipong.assignment.feature.user.controller;


import com.kittipong.assignment.feature.user.controller.domain.AuthenRequest;
import com.kittipong.assignment.feature.user.controller.domain.UserRequest;
import com.kittipong.assignment.feature.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@TestComponent
public class UserControllerTests {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;



    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Delete_user_Success() throws Exception {
        //Given
        String  authHeader = "1223344";


        //When
        ResponseEntity response = userController.deleteUser(authHeader);

        //Then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);


    }

    @Test
    void Save_user_Success() throws Exception {
        //Given
        UserRequest user = new UserRequest("User","pass","name","surname","2013/01/06");


        //When
        ResponseEntity response = userController.saveUser(user);

        //Then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);


    }

    @Test
    void Create_Authen_Success() throws Exception {
        //Given
        AuthenRequest authenRequest = new AuthenRequest("User","pass");
        String token = "token";
        given(userService.login(any(AuthenRequest.class))).willReturn(token);

        //When
        ResponseEntity response = userController.createAuthenticationToken(authenRequest);

        HttpHeaders headers = response.getHeaders();
        String authen = headers.getFirst("Authorization");
        //Then
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);


    }
};