package com.mate.album30.domain.albumTalk.service;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.albumTalk.repository.ChatRepository;
import com.mate.album30.domain.albumTalk.repository.ChatRoomRepository;
import com.mate.album30.domain.member.entity.Member;
import com.mate.album30.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;  // Chat 데이터를 처리할 Repository
    private final ChatRoomRepository chatRoomRepository;  // ChatRoom 데이터를 처리할 Repository
    private final MemberRepository memberRepository;
    @PersistenceContext
    private EntityManager entityManager;  // EntityManager 주입

    @Transactional
    public Chat decideNormalChatOrQuickChat(Long roomId, ChatDto receivedChatDto) {
        Member member = memberRepository.findMemberByNickName(receivedChatDto.getSender());
        // 메시지 유형에 따른 처리
        Chat chat;
        switch (receivedChatDto.getType()) {
            case "message":
                chat = createChat(roomId, receivedChatDto.getSender(), receivedChatDto.getMessage());
                break;
            case "address":
                chat = createQuicklChat(roomId, receivedChatDto.getSender(), "주소 정보", receivedChatDto.getType());
                break;
            case "accont" :
                // Todo 계좌 정보로 수정
                chat = createQuicklChat(roomId, receivedChatDto.getSender(), "계좌정보", receivedChatDto.getType());
                break;
            case "delivery":
                chat = createQuicklChat(roomId, receivedChatDto.getSender(), receivedChatDto.getMessage(), receivedChatDto.getType());
                break;

            default:
                throw new IllegalArgumentException("Unsupported chat type: " + receivedChatDto.getType());
        }
        return chat;
    }



    // 채팅 메시지 저장하는 메서드
@Transactional  // 트랜잭션 관리 어노테이션 추가
    public Chat createChat(Long roomId, String sender, String message) {
        // ChatRoom 엔티티를 roomId로 조회 (Optional 처리)
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid chat room ID: " + roomId));

        // chatRoom이 detached 상태가 아니므로 merge는 불필요
        // 이 부분에서 직접 영속화된 chatRoom을 사용할 수 있음

        // Chat 객체 생성
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .message(message)
                .sendAt(LocalDateTime.now())
                .isChecked(false)
                .type("message")
                .build();

        // Chat 객체를 데이터베이스에 저장
        return chatRepository.save(chat);
    }
    @Transactional
    public Chat createQuicklChat(Long roomId, String sender, String message, String type) {
    // ChatRoom 엔티티를 roomId로 조회 (Optional 처리)
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid chat room ID: " + roomId));
        return chatRepository.save(
                Chat.builder()
                        .chatRoom(chatRoom)
                        .sender(sender)
                        .message(message)
                        .type(type)
                        .build()
        );

    }


    public List<ChatDto> getChatHistory(Long roomId) {
    // TOdo 예외 처리
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found with id: " + roomId));

        // Chats 정렬 및 변환
        return chatRoom.getChats().stream()
                .sorted((chat1, chat2) -> chat1.getCreatedAt().compareTo(chat2.getCreatedAt())) // 날짜순 정렬
                .map(chat -> ChatDto.builder()
                        .chatId(chat.getChatId())
                        .chatRoomId(chatRoom.getChatRoomId())
                        .sender(chat.getSender())
                        .message(chat.getMessage())
                        .build())
                .collect(Collectors.toList());
    }
}

