package com.mate.album30.domain.albumTalk.entity;

import com.mate.album30.domain.common.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Builder@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    private LocalDateTime sendAt;
    private boolean isChecked;

    private Long messageTypeId;
    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 채팅방

    /**
     * 채팅 생성
     * @param room 채팅 방
     * @param sender 보낸이
     * @param message 내용
     * @return Chat Entity
     */
    public static Chat createChat(ChatRoom room, String sender, String message) {
        return Chat.builder()
                .chatRoom(room)
                .sender(sender)
                .message(message)
                .build();
    }

}
