package com.mate.album30.domain.albumTalk.controller;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.dto.ChatResponseDto;
import com.mate.album30.domain.albumTalk.repository.ChatRepository;
import com.mate.album30.domain.albumTalk.service.AlbumTalkService;
import com.mate.album30.domain.albumTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class AlbumTalkController {
    final AlbumTalkService albumTalkService;
    private final ChatService chatService;

    @GetMapping("/{roomId}/history")
    public List<ChatDto> getChatHistory(@PathVariable Long roomId) {
        return chatService.getChatHistory(roomId);
    }

    @GetMapping(value = "/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatResponseDto.ChatRoomResponseDto getChatRoomInfo(@PathVariable Long roomId) {
        return albumTalkService.getChatRoomInfo(roomId);
    }


}
