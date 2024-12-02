package com.mate.album30.domain.orderMatch.controller;

import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.common.enums.Role;
import com.mate.album30.domain.orderMatch.dto.OrderRequestDto;
import com.mate.album30.domain.orderMatch.entity.Match;
import com.mate.album30.domain.orderMatch.entity.Order;
import com.mate.album30.domain.orderMatch.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 거래 등록 리스트 조회
    @GetMapping("/sorted")
    public List<Order> getSortedOrders(
            @RequestParam Role role,
            @RequestParam OrderStatus orderStatus
    ) {
        return orderService.getSortedOrders(role, orderStatus);
    }

    // 매칭된 거래 리스트 조회 -> 생성된 채팅방 id 반환
    @GetMapping("/match")
    public List<Match> getMatchedOrders() {
        return orderService.matchOrders();
    }

    // 매칭된 결과를 내림차순 정렬하여 반환
    @GetMapping("/matching/history")
    public ResponseEntity<List<Match>> getSortedMatches() {
        List<Match> sortedMatches = orderService.getSortedMatchesByDate();
        return ResponseEntity.ok(sortedMatches);
    }


}
