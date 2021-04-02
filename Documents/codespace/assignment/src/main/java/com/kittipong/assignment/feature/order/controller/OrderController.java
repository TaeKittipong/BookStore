package com.kittipong.assignment.feature.order.controller;


import com.kittipong.assignment.feature.order.controller.domain.OrderRequest;
import com.kittipong.assignment.feature.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping (value = "/users/orders")
    public ResponseEntity<?> order(@Valid @RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String authHeader) throws Exception {
        return ResponseEntity.ok(orderService.order(orderRequest,authHeader));

    }
    @GetMapping (value = "/users")
    public ResponseEntity<?> getUserOrder(@Valid @RequestHeader("Authorization") String authHeader) throws Exception {
        return ResponseEntity.ok(orderService.getUserOrder(authHeader));

    }
}
