package com.mate.album30.domain.member.entity;

import com.mate.album30.domain.album.entity.Bookmark;
import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.member.entity.enums.Provider;
import com.mate.album30.domain.orderMatch.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Builder
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(unique = true)
    private String providerId;

    private String nickName;

    @Column
    private String name;

    @Column
    private String birthDate;

    @Column
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private Boolean isActivated;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShippingAddress> shippingAddressList = new ArrayList<>();
}
