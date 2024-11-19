package com.mate.album30.domain.orderMatch.service;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.repository.AlbumRepository;
import com.mate.album30.domain.member.entity.Member;
import com.mate.album30.domain.member.repository.MemberRepository;
import com.mate.album30.domain.orderMatch.dto.OrderRequestDto;
import com.mate.album30.domain.orderMatch.entity.Order;
import com.mate.album30.domain.orderMatch.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;

    public Order createOrder(OrderRequestDto orderRequestDto){
        //Album 조회
        Album album = albumRepository.findById(orderRequestDto.getAlbumId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

        // Member 조회
        Member member = memberRepository.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Order 생성
        Order order = Order.builder()
                .isCompleted(orderRequestDto.getIsCompleted())
                .quantity(orderRequestDto.getQuantity())
                .components(orderRequestDto.getComponents())
                .price(orderRequestDto.getPrice())
                .deliveryType(orderRequestDto.getDeliveryType())
                .period(orderRequestDto.getPeriod())
                .role(orderRequestDto.getRole())
                .album(album)
                .member(member)
                .build();

        return orderRepository.save(order);
    }
}
