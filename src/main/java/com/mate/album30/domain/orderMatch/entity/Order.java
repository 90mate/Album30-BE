package com.mate.album30.domain.orderMatch.entity;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.common.enums.DeliveryType;
import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.common.enums.Role;
import com.mate.album30.domain.orderMatch.entity.enums.Components;
import com.mate.album30.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus isCompleted;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Components components;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @Column(nullable = false)
    private Integer period;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
