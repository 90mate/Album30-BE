package com.mate.album30.domain.albumTalk.controller;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.dto.ChatResponseDto;
import com.mate.album30.domain.albumTalk.repository.ChatRepository;
import com.mate.album30.domain.albumTalk.service.AlbumTalkService;
import com.mate.album30.domain.albumTalk.service.ChatService;
import com.mate.album30.global.auth.annotation.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
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

    @PostMapping("/{chatRoom}")
    public void changeChatRoomStatus(@LoginInfo Long memberId,
                                     @PathVariable(name = "chatRoomId") Long chatRoomId,
                                     @RequestParam(name = "status") String orderStatus


    ) {
        // Todo 검증하기
        // order에 완료 표시 & 두명다 완료 시 -> chatRoom도 상태 변환
        albumTalkService.changeStatusOrderAndChatRoom(chatRoomId, orderStatus, memberId);

    }
    // Todo
    // quick message보내기(배송지 리스트 중 선택, 계좌, 사진/동영상)


}
