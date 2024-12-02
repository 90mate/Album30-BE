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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        return orderRepository.save(order);
    }

    // 조건에 맞는 Order 목록 조회
    public List<Order> getSortedOrders(Role role, OrderStatus orderStatus) {
        Sort sort = null;

        if (role == Role.SELLER && orderStatus == OrderStatus.ONGOING) {
            // 판매 등록 목록: 가격 오름차순 -> created_at 오름차순
            sort = Sort.by(Sort.Order.asc("price"), Sort.Order.asc("createdAt"));
        } else if (role == Role.BUYER && orderStatus == OrderStatus.ONGOING) {
            // 구매 등록 목록: 가격 내림차순 -> created_at 오름차순
            sort = Sort.by(Sort.Order.desc("price"), Sort.Order.asc("createdAt"));
        }

        return orderRepository.findByRoleAndOrderStatus(role, orderStatus, sort);
    }

    // 매칭된 거래를 반환하는 메서드
    public List<Match> matchOrders() {
        // 구매자와 판매자 거래를 각각 조회
        List<Order> buyers = orderRepository.findByRole(Role.BUYER);
        List<Order> sellers = orderRepository.findByRole(Role.SELLER);

        List<Match> matchings = new ArrayList<>();

        // 매칭 로직: 가격과 구성품이 같을 때 매칭
        for (Order buyer : buyers) {
            for (Order seller : sellers) {
                if (buyer.getPrice().equals(seller.getPrice()) &&
                        buyer.getComponents().equals(seller.getComponents())) {

                    // 매칭된 결과를 저장
                    Match matching = new Match();
                    matching.setBuyer(buyer);
                    matching.setSeller(seller);


                    matchings.add(matching);

                    // 매칭이 완료된 거래를 제외
                    sellers.remove(seller);
                    break;
                }
            }
        }

        // 매칭 결과를 저장소에 저장
        matchingRepository.saveAll(matchings);
        return matchings;
    }

    // 매칭 결과를 매칭 날짜에 따라 내림차순으로 정렬하는 메서드
    public List<Match> getSortedMatchesByDate() {
        List<Match> matches = matchingRepository.findAll();
        matches.sort(Comparator.comparing(Match::getCreatedAt).reversed());
        return matches;
    }
}
