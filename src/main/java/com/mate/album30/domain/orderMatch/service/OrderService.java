package com.mate.album30.domain.orderMatch.service;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.repository.AlbumRepository;
import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.common.enums.Role;
import com.mate.album30.domain.member.entity.Member;
import com.mate.album30.domain.member.repository.MemberRepository;
import com.mate.album30.domain.orderMatch.dto.OrderRequestDto;
import com.mate.album30.domain.orderMatch.entity.Match;
import com.mate.album30.domain.orderMatch.entity.Order;
import com.mate.album30.domain.orderMatch.repository.MatchingRepository;
import com.mate.album30.domain.orderMatch.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;
    private final MatchingRepository matchingRepository; // 매칭된 거래 저장소

    // Order 생성
    public Order createOrder(OrderRequestDto orderRequestDto){
        //Album 조회
        Album album = albumRepository.findById(orderRequestDto.getAlbumId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

        // Member 조회
        Member member = memberRepository.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // Order 생성
        Order order = Order.builder()
                .orderStatus(orderRequestDto.getOrderStatus())
                .quantity(orderRequestDto.getQuantity())
                .components(orderRequestDto.getComponents())
                .price(orderRequestDto.getPrice())
                .deliveryType(orderRequestDto.getDeliveryType())
                .period(orderRequestDto.getPeriod())
                .role(orderRequestDto.getRole())
                .album(album)
                .member(member)
                .build();

        // 매칭 로직
        List<Order> potentialMatches;
        if (order.getRole() == Role.BUYER) {
            // 구매자라면 판매자 목록 조회
            potentialMatches = orderRepository.findByRole(Role.SELLER);
        } else if (order.getRole() == Role.SELLER) {
            // 판매자라면 구매자 목록 조회
            potentialMatches = orderRepository.findByRole(Role.BUYER);
        } else {
            throw new IllegalArgumentException("Invalid role");
        }

        for (Order potentialMatch : potentialMatches) {
            if (order.getPrice().equals(potentialMatch.getPrice()) &&
                    order.getComponents().equals(potentialMatch.getComponents())) {

                // 매칭된 결과를 저장
                Match match = new Match();
                match.setBuyer(order.getRole() == Role.BUYER ? order : potentialMatch);
                match.setSeller(order.getRole() == Role.SELLER ? order : potentialMatch);

                matchingRepository.save(match);

                // 매칭된 Order는 처리 완료 상태로 업데이트
                potentialMatch.setOrderStatus(OrderStatus.MATCHED);
                order.setOrderStatus(OrderStatus.MATCHED);
                orderRepository.save(potentialMatch);
                break;
            }
        }

        // 매칭되지 않은 Order 저장
        return orderRepository.save(order);
    }

    public Map<String, List<Order>> getOrdersByUserAndStatus(Long memberId) {
        Map<String, List<Order>> result = new HashMap<>();

        // BUYER 기준으로 상태별 조회
        result.put("BUYER_ONGOING", orderRepository.findByMember_MemberIdAndRoleAndOrderStatus(memberId, Role.BUYER, OrderStatus.ONGOING));
        result.put("BUYER_MATCHED", orderRepository.findByMember_MemberIdAndRoleAndOrderStatus(memberId, Role.BUYER, OrderStatus.MATCHED));
        result.put("BUYER_COMPLETION", orderRepository.findByMember_MemberIdAndRoleAndOrderStatus(memberId, Role.BUYER, OrderStatus.COMPLETION));

        // SELLER 기준으로 상태별 조회
        result.put("SELLER_ONGOING", orderRepository.findByMember_MemberIdAndRoleAndOrderStatus(memberId, Role.SELLER, OrderStatus.ONGOING));
        result.put("SELLER_MATCHED", orderRepository.findByMember_MemberIdAndRoleAndOrderStatus(memberId, Role.SELLER, OrderStatus.MATCHED));
        result.put("SELLER_COMPLETION", orderRepository.findByMember_MemberIdAndRoleAndOrderStatus(memberId, Role.SELLER, OrderStatus.COMPLETION));

        return result;
    }


    // 조건에 맞는 Order 목록 조회
    public List<Order> getSortedOrders(Long albumId, Role role, OrderStatus orderStatus) {
        Sort sort = null;

        if (role == Role.SELLER && orderStatus == OrderStatus.ONGOING) {
            // 판매 등록 목록: 가격 오름차순 -> created_at 오름차순
            sort = Sort.by(Sort.Order.asc("price"), Sort.Order.asc("createdAt"));
        } else if (role == Role.BUYER && orderStatus == OrderStatus.ONGOING) {
            // 구매 등록 목록: 가격 내림차순 -> created_at 오름차순
            sort = Sort.by(Sort.Order.desc("price"), Sort.Order.asc("createdAt"));
        }

        return orderRepository.findByRoleAndOrderStatusAndAlbum_AlbumId(role, orderStatus, albumId, sort);
    }

    // 특정 앨범의 매칭된 거래를 반환하는 메서드
    public List<Match> getMatchedOrders(Long albumId) {
        return matchingRepository.findByAlbumId(albumId);
    }

    // 특정 앨범의 매칭 결과를 매칭 날짜에 따라 내림차순으로 정렬하는 메서드
    public List<Match> getSortedMatchesByDate(Long albumId) {
        List<Match> matches = matchingRepository.findByAlbumId(albumId);
        matches.sort(Comparator.comparing(Match::getCreatedAt).reversed());
        return matches;
    }
}
