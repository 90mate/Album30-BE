package com.mate.album30.domain.albumTalk.entity;

import com.mate.album30.domain.global.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String content;
    private LocalDateTime sendAt;
    private boolean isChecked;

//    // 발신자와 메시지 타입 연관 관계
//    @ManyToOne
//    @JoinColumn(name = "sender_id")
//    private Member sender;

    private Long messageTypeId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 채팅방
}
