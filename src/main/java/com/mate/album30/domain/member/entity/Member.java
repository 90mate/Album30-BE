// Member.java
package com.mate.album30.domain.member.entity;

import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.global.BaseEntity;
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

    private String nickName;
    private String name;
    private String birthDay;
    private String phoneNumber;
    private Boolean isActivated;

    // 양방향 연관 관계 설정 (구매한 거래)
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<ChatRoom> boughtChatRoomList = new ArrayList<>();

    // 양방향 연관 관계 설정 (판매한 거래)
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<ChatRoom> soldChatRoomList = new ArrayList<>();

    public void addBoughtAlbumTalk(ChatRoom chatRoom) {
        this.boughtChatRoomList.add(chatRoom);
        chatRoom.setBuyer(this);
    }

    public void addSoldAlbumTalk(ChatRoom chatRoom) {
        this.soldChatRoomList.add(chatRoom);
        chatRoom.setSeller(this);
    }
}
