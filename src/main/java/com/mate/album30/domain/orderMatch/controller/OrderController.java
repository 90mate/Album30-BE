package com.mate.album30.domain.orderMatch.controller;

import com.mate.album30.domain.orderMatch.dto.OrderRequestDto;
import com.mate.album30.domain.orderMatch.entity.Order;
import com.mate.album30.domain.orderMatch.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Order 생성
    @PostMapping("/register")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        Order createdOrder = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(createdOrder);
    }


}
