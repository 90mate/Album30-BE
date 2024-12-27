package com.mate.album30.domain.orderMatch.controller;

import com.mate.album30.domain.album.entity.Album;
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
import java.util.Map;

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
    @GetMapping("/orders")
    public List<Order> getSortedOrders(
            @RequestParam Long albumId,
            @RequestParam Role role,
            @RequestParam OrderStatus orderStatus
    ) {
        return orderService.getSortedOrders(albumId, role, orderStatus);
    }


    // 매칭된 거래 리스트 조회
    @GetMapping("/matches")
    public ResponseEntity<List<Match>> getMatchedOrders(@PathVariable Long albumId) {
        return ResponseEntity.ok(orderService.getMatchedOrders(albumId));

    }

    // 매칭된 결과를 내림차순 정렬하여 반환
    @GetMapping("/matches/sorted")
    public ResponseEntity<List<Match>> getSortedMatchesByDate(@PathVariable Long albumId) {
        return ResponseEntity.ok(orderService.getSortedMatchesByDate(albumId));
    }

    // 사용자 별 거래 리스트 반환
    @GetMapping("/user/{memberId}")
    public ResponseEntity<Map<String, List<Order>>> getOrdersByUserAndStatus(@PathVariable Long memberId) {
        return ResponseEntity.ok(orderService.getOrdersByUserAndStatus(memberId));
    }

}
