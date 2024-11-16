package com.mate.album30.domain.albumTalk.service;

import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.repository.AlbumTalkRepository;
import com.mate.album30.domain.albumTalk.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class ChatService {

    private final AlbumTalkRepository chatRepository;  // Chat 데이터를 처리할 Repository
    private final ChatRoomRepository chatRoomRepository;  // Chat 데이터를 처리할 Repository

    // 채팅 메시지 저장하는 메서드
    public Chat createChat(Long roomId, String sender, String message) {
        // Chat 객체 생성
        Chat chat = Chat.builder()
                .chatRoom(chatRoomRepository.findById(roomId).get())
                .sender(sender)
                .message(message)
                .build();

        // Chat 객체를 데이터베이스에 저장
        return chatRepository.save(chat);
    }


}
