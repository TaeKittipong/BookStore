package com.kittipong.assignment.feature.order.controller;


import com.kittipong.assignment.feature.order.controller.domain.OrderRequest;
import com.kittipong.assignment.feature.order.controller.domain.OrderResponse;
import com.kittipong.assignment.feature.order.controller.domain.UserOrderResponse;
import com.kittipong.assignment.feature.order.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@TestComponent
public class OrderControllerTests {
    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;



    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Post_New_Order_Success() throws Exception{
        //Given
        List<Integer> booksId = Arrays.asList(1,4);
        OrderRequest orderRequest = new OrderRequest(booksId);
        OrderResponse orderResponse = new OrderResponse(123.50);
        String authHeader ="1112333";
        given(orderService.order(any(OrderRequest.class),anyString())).willReturn(orderResponse);

        //when
        ResponseEntity<?> result  = orderController.order(orderRequest,  authHeader);
        OrderResponse response = (OrderResponse) result.getBody();


        //Then
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(123.50,response.getPrice());

    }


    @Test
    void Get_All_Order_From_Logged_User_Success() throws Exception{
        //Given
        List<Integer> booksId = Arrays.asList(1,4);
        UserOrderResponse userOrderResponse = new UserOrderResponse("name","surname","2012/12/03",booksId);
        String authHeader ="1112333";
        given(orderService.getUserOrder(anyString())).willReturn(userOrderResponse);
        //when
        ResponseEntity<?> result  = orderController.getUserOrder(authHeader);
        UserOrderResponse response = (UserOrderResponse) result.getBody();


        //Then
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals("name",response.getName());
        Assertions.assertEquals("surname",response.getSurname());
        Assertions.assertEquals(booksId.size(),response.getBooks().size());

    }


}
