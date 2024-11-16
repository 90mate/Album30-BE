package com.mate.album30.domain.albumTalk.entity;

import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.common.Role;
import com.mate.album30.domain.member.entity.Member;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    private Long productId; // 상품 ID
    private Long orderId;   // 주문 ID

    @Enumerated(EnumType.STRING)
    private Role role; // 구매자/판매자 역할 Enum

    // 구매자와 판매자의 연관 관계 설정
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>(); // 메시지 리스트

    public void addMessage(Chat chat) {
        this.chats.add(chat);
        chat.setChatRoom(this);
    }
    public Long getChatRoomId(ChatRoom chatRoom) {
       return chatRoomId;
    }

}
