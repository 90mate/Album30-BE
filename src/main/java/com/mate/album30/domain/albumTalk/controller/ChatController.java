package com.mate.album30.domain.albumTalk.controller;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/send/{roomId}")  // 클라이언트에서 보낸 메시지를 처리
    @SendTo("/room/{roomId}")   // 응답을 해당 방에 구독한 모든 클라이언트에게 전송
    public ChatDto chat(@DestinationVariable Long roomId, ChatDto receivedChatDto) {
        // 채팅 저장
        Chat chat = chatService.createChat(roomId, receivedChatDto.getSender(), receivedChatDto.getMessage());
        System.out.println("Chat saved: " + chat);  // 로그 추가
        return ChatDto.builder()
                .chatRoomId(roomId)
                .sender(chat.getSender())
                .message(chat.getMessage())
                .build();
    }
}
