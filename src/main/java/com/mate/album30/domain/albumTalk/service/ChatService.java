package com.mate.album30.domain.albumTalk.service;

import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.albumTalk.repository.AlbumTalkRepository;
import com.mate.album30.domain.albumTalk.repository.ChatRoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//@Service
//@Data
//
//@RequiredArgsConstructor
//public class ChatService {
//
//    private final AlbumTalkRepository chatRepository;  // Chat 데이터를 처리할 Repository
//    private final ChatRoomRepository chatRoomRepository;  // Chat 데이터를 처리할 Repository
//
//    // 채팅 메시지 저장하는 메서드
//    public Chat createChat(Long roomId, String sender, String message) {
//        // ChatRoom 엔티티를 roomId로 조회 (Optional 처리)
//        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid chat room ID: " + roomId));
//
//        // Chat 객체 생성
//        // Chat 객체 생성
//        Chat chat = Chat.builder()
//                .chatRoom(chatRoom)
//                .sender(sender)
//                .message(message)
//                .sendAt(LocalDateTime.now())
//                .isChecked(false)
//                .messageTypeId(1L)
//                .build();
////        log.info("Chat created: {}", chat);  // 로그 추가
//
//        // Chat 객체를 데이터베이스에 저장
//        return chatRepository.save(chat);
//    }
//
//
//}

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AlbumTalkRepository chatRepository;  // Chat 데이터를 처리할 Repository
    private final ChatRoomRepository chatRoomRepository;  // ChatRoom 데이터를 처리할 Repository
    @PersistenceContext
    private EntityManager entityManager;  // EntityManager 주입

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
                .messageTypeId(1L)
                .build();

        // Chat 객체를 데이터베이스에 저장
        return chatRepository.save(chat);
    }
}

