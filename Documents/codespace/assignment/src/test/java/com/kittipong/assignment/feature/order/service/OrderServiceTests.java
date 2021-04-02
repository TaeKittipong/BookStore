package com.kittipong.assignment.feature.order.service;

import com.kittipong.assignment.common.datasource.entities.OrderEntity;
import com.kittipong.assignment.common.datasource.entities.UserEntity;
import com.kittipong.assignment.common.datasource.repo.OrderRepository;
import com.kittipong.assignment.feature.book.controller.domain.BookResponseController;
import com.kittipong.assignment.feature.book.service.BookService;
import com.kittipong.assignment.feature.order.controller.domain.OrderRequest;
import com.kittipong.assignment.feature.order.controller.domain.OrderResponse;
import com.kittipong.assignment.feature.order.controller.domain.UserOrderResponse;
import com.kittipong.assignment.feature.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@TestComponent
public class OrderServiceTests {
    @InjectMocks
    OrderService orderService;

    @Mock
    BookService bookService;
    @Mock
    UserService userService;
    @Mock
    OrderRepository orderRepository;

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
        String authHeader ="1112333";

        BookResponseController book1 = new BookResponseController(1,"name1","author1",100.50,false);
        BookResponseController book2 = new BookResponseController(2,"name2","author2",200.50,true);
        BookResponseController book3 = new BookResponseController(3,"name3","author3",300.50,false);
        BookResponseController book4 = new BookResponseController(4,"name4","author4",400.50,true);
        List<BookResponseController> allBooks = Arrays.asList(book1, book2,book3,book4);

        given(bookService.getBooks()).willReturn(allBooks);
        //When
        OrderResponse result = orderService.order(orderRequest,authHeader);

        //Then
        verify(orderRepository,atLeastOnce()).saveAll(any(List.class));
        Assertions.assertEquals(501,result.getPrice());

    }

    @Test
    void Get_user_Order_Success() throws Exception{
        //Given
        OrderEntity orderEntity1 = new OrderEntity("User",1);
        OrderEntity orderEntity2 = new OrderEntity("User",4);
        List<OrderEntity> orderEntities = Arrays.asList(orderEntity1,orderEntity2);
        UserEntity userEntity = new UserEntity("User","password","name","surname","2016/02/24");


        given(userService.getUser(anyString())).willReturn(userEntity);
        given(orderRepository.findByUserName(anyString())).willReturn(orderEntities);
        given(userService.getUserFromHeader(anyString())).willReturn("User");
        //When
        UserOrderResponse result = orderService.getUserOrder(anyString());

        //Then

        Assertions.assertEquals(orderEntities.size(),result.getBooks().size());
        Assertions.assertEquals(userEntity.getName(),result.getName());

    }

    @Test
    void Delete_user_Order_Success() throws Exception {

        //Given
        String user = "User";

        //When
        orderService.delete(anyString());

        //Then
        verify(orderRepository,atLeastOnce()).deleteByUserName(anyString());

    }


}
