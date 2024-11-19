package com.mate.album30.domain.albumTalk.dto;

import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public class ChatRequestDto {

    @Builder
    public static class ChatRoomRequestDto {
        /**
         * 
         *
         * */
        Long chatRoomId;
        String image;
        Status status;
        String title;
        LocalDateTime updatedAt;
        String recentChat;
        String nickName;

        public static ChatRoomRequestDto convertToChatRoomDto(ChatRoom room, String image) {
            return ChatRoomRequestDto.builder()
                    .chatRoomId(room.getChatRoomId())
                    .image(image)
                    .status()
                    .title(room.)

                    .build();
        }

    }
}
