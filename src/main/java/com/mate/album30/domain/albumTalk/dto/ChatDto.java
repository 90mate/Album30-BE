package com.mate.album30.domain.albumTalk.dto;

import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class ChatDto {
    private Long chatId;  // 메시지 ID
    private String sender;   // 보낸 사람
    private String message;  // 메시지 내용
    private LocalDateTime sendAt;  // 메시지 발송 시간
    private boolean isChecked;  // 메시지 읽음 여부
    private Long messageTypeId;  // 메시지 유형 (예: 텍스트, 이미지 등)
    private Long chatRoomId;  // 채팅방 ID

    // Chat 엔티티를 ChatDto로 변환하는 메서드
    public static ChatDto createChatDto(Chat chat) {
        return ChatDto.builder()
//                .chatId(chat.getChatId())
                .sender(chat.getSender())
                .message(chat.getMessage())
//                .sendAt(chat.getSendAt())
//                .isChecked(chat.isChecked())
//                .messageTypeId(chat.getMessageTypeId())
                .chatRoomId(chat.getChatRoom().getChatRoomId())  // ChatRoom의 ID 가져오기
                .build();
    }

}

















