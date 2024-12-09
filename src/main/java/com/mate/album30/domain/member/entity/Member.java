package com.mate.album30.domain.member.entity;

import com.mate.album30.domain.album.entity.Bookmark;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.member.entity.enums.Provider;
import com.mate.album30.domain.albumTalk.entity.*;
import com.mate.album30.domain.orderMatch.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId; // MySQL에서 기본 키

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(unique = true)
    private String nickName;

    @Column
    private String name;

    @Column
    private String birthDate;

    @Column
    private String phoneNumber;

    @Column
    private Boolean isActivated;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingAddress> shippingAddressList = new ArrayList<>();
}
