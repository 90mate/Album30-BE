package com.mate.album30.domain.albumTalk.entity;

import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.common.Role;
import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.member.entity.Member;
import com.mate.album30.domain.orderMatch.entity.Match;
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

//    private Long productId; // 상품 ID

//    @Enumerated(EnumType.STRING)
//    private Role role; // 구매자/판매자 역할 Enum

    @OneToOne(cascade = CascadeType.ALL)
    private Match match;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>(); // 메시지 리스트

    private boolean isCompleted;


    /**
     * sql join으로 필요한 데이터
     * member : nickname, account, address
     * order : orderId, delveryType, price
     * product : artist, group,
     * */

    public void addMessage(Chat chat) {
        this.chats.add(chat);
        chat.setChatRoom(this);
    }
    public Long getChatRoomId(ChatRoom chatRoom) {
       return chatRoomId;
    }

}
