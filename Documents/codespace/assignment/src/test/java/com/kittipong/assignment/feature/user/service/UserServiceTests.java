package com.kittipong.assignment.feature.user.service;

import com.kittipong.assignment.common.config.JwtTokenUtil;
import com.kittipong.assignment.common.datasource.entities.UserEntity;
import com.kittipong.assignment.common.datasource.repo.UserRepository;
import com.kittipong.assignment.feature.order.service.OrderService;
import com.kittipong.assignment.feature.user.controller.domain.AuthenRequest;
import com.kittipong.assignment.feature.user.controller.domain.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@TestComponent
public class UserServiceTests {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder bcryptEncoder;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    OrderService orderService;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Delete_user_Success() throws Exception {
        //Given
        String  authHeader = "123123213";
        given(jwtTokenUtil.getUsernameFromToken(anyString())).willReturn(anyString());

        //When
        userService.delete(authHeader);


        //Then
        verify(userRepository,atLeastOnce()).deleteByUserName(anyString());
        verify(orderService,atLeastOnce()).delete(anyString());

    }

    @Test
    void Save_User_Success() throws Exception {
        //Given
        UserRequest userRequest = new UserRequest("userName","password","name","surname","2012/05/06");

        //When
        userService.save(userRequest);


        //Then
        verify(userRepository,atLeastOnce()).save(any(UserEntity.class));

    }


    @Test
    void Login_Success() throws Exception {
        //Given
        AuthenRequest authenRequest = new AuthenRequest("username","password");
        UserEntity userEntity = new UserEntity("username","password","name","surname","22/05/2013");
        given(jwtTokenUtil.generateToken(any(UserDetails.class))).willReturn("token");
        given(userRepository.findByUserName(anyString())).willReturn(userEntity);

        //When
        userService.login(authenRequest);


        //Then
        verify(userRepository,atLeastOnce()).findByUserName(anyString());

    }

    @Test
    void Login_Fail_NotFound_User() throws Exception {
        //Given
        AuthenRequest authenRequest = new AuthenRequest("username","password");
        given(jwtTokenUtil.generateToken(any(UserDetails.class))).willReturn("token");
        given(userRepository.findByUserName(anyString())).willReturn(any(UserEntity.class));

        //When
        try {
            userService.login(authenRequest);
        }catch (UsernameNotFoundException e){
            //Then
            verify(userRepository,atLeastOnce()).findByUserName(anyString());
        }




    }
}
