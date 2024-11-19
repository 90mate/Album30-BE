package com.mate.album30.domain.albumTalk.service;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.dto.ChatResponseDto;
import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.albumTalk.repository.ChatRepository;
import com.mate.album30.domain.albumTalk.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumTalkService {
    private final ChatRepository chatRepository;  // Chat 데이터를 처리할 Repository
    private final ChatRoomRepository chatRoomRepository;  // ChatRoom 데이터를 처리할 Repository


    public ChatResponseDto.ChatRoomResponseDto getChatRoomInfo(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));

        return ChatResponseDto.ChatRoomResponseDto.convertToChatRoomDto(room);
    }

}
