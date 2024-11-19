package com.mate.album30.domain.albumTalk.controller;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class); // 로그 객체 생성
    private final ChatService chatService;

    @MessageMapping("/{roomId}") // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes에서 적용한 건 앞에 생략
    @SendTo("/room/{roomId}")   // 구독하고 있는 장소로 메시지 전송 (목적지) -> WebSocketConfig Broker에서 적용한 건 앞에 붙어줘야됨
    public ChatDto chat(@DestinationVariable Long roomId, ChatDto receivedChatDto) {
        logger.info("Received message from sender: {} for roomId: {}", receivedChatDto.getSender(), roomId);

        // 채팅 저장
        Chat chat = chatService.createChat(roomId, receivedChatDto.getSender(), receivedChatDto.getMessage());

        logger.info("Chat saved: {}", chat);  // 저장된 채팅 객체 로그 출력

        return ChatDto.builder()
                .chatRoomId(roomId)
                .sender(chat.getSender())
                .message(chat.getMessage())
                .build();
    }


}
