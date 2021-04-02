package com.kittipong.assignment.feature.order.service;

import com.kittipong.assignment.common.datasource.entities.OrderEntity;
import com.kittipong.assignment.common.datasource.entities.UserEntity;
import com.kittipong.assignment.common.datasource.repo.OrderRepository;
import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.common.exception.BookStoreServiceDataNotFoundException;
import com.kittipong.assignment.feature.book.controller.domain.BookResponseController;
import com.kittipong.assignment.feature.book.service.BookService;
import com.kittipong.assignment.feature.order.controller.domain.OrderRequest;
import com.kittipong.assignment.feature.order.controller.domain.OrderResponse;
import com.kittipong.assignment.feature.order.controller.domain.UserOrderResponse;
import com.kittipong.assignment.feature.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    OrderRepository orderRepository;
    @Transactional
    public OrderResponse order(OrderRequest orderRequest,String authHeader) throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
        List<Integer> orders = orderRequest.getOrders();
        double price = calculatePrice(orders);
        String user = userService.getUserFromHeader(authHeader);
        List<OrderEntity>  orderEntities = orders.stream().map(order -> new OrderEntity(user,order)).collect(Collectors.toList());
        orderRepository.saveAll(orderEntities);

        return new OrderResponse(price);
    }
    private double calculatePrice( List<Integer> orders) throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
        double price = 0;
        List<BookResponseController> allBooks =  bookService.getBooks();
        allBooks = allBooks.stream().filter(book -> orders.stream()
                .anyMatch(order -> order == book.getId()))
                .collect(Collectors.toList());
        return allBooks.stream().mapToDouble(BookResponseController::getPrice).sum();

    }

    public UserOrderResponse getUserOrder(String authHeader) {
        String user = userService.getUserFromHeader(authHeader);
        UserEntity userEntity = userService.getUser(user);
        List<OrderEntity>  orderEntities =  orderRepository.findByUserName(user);
        List<Integer> orderIds = orderEntities.stream().map(OrderEntity::getBookId).collect(Collectors.toList());

        return new UserOrderResponse(userEntity.getName(),userEntity.getSurname(),userEntity.getDateOfBirth(),orderIds);

    }

@Transactional(propagation=Propagation.SUPPORTS)
    public void delete(String user) {
        orderRepository.deleteByUserName(user);
    }
}
