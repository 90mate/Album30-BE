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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class); // 로그 객체 생성
    private final ChatService chatService;

    @MessageMapping("/{roomId}") // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes에서 적용한 건 앞에 생략
    @SendTo("/room/{roomId}")   // 구독하고 있는 장소로 메시지 전송 (목적지) -> WebSocketConfig Broker에서 적용한 건 앞에 붙어줘야됨
    public ChatDto chat(@DestinationVariable Long roomId, ChatDto receivedChatDto) {
        logger.info("Received message from sender: {} for roomId: {}", receivedChatDto.getSenderId(), roomId);
        // Todo 토큰값으로 메세지 전송 가능 여부 판별

        // 메시지 유형에 따른 처리
        Chat chat = chatService.decideNormalChatOrQuickChat(roomId, receivedChatDto);

        logger.info("Chat saved: {}", chat);

        return ChatDto.builder()
                .chatRoomId(roomId)
                .senderId(chat.getSenderId())
                .message(chat.getMessage())
                .type(chat.getType())
                .build();
    }


    //    @PostMapping("/{chatRoom}")
//    public void sendQuickIconMessage(@PathVariable(name = "chatRoomId") Long chatRoomId,
//                                   @RequestParam(name = "icon") String iconName,
//                               @RequestParam(name = "memberId") Long memberId
//
//    ) {
//        // Todo 검증하기
//        // order에 완료 표시 & 두명다 완료 시 -> chatRoom도 상태 변환
//        albumTalkService.changeStatusOrderAndChatRoom(chatRoomId, orderStatus, memberId);
//
//    }


}
