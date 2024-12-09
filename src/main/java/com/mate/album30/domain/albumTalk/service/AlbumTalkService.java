package com.mate.album30.domain.albumTalk.service;

import com.mate.album30.domain.albumTalk.dto.ChatDto;
import com.mate.album30.domain.albumTalk.dto.ChatResponseDto;
import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.albumTalk.repository.ChatRepository;
import com.mate.album30.domain.albumTalk.repository.ChatRoomRepository;
import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.common.enums.Role;
import com.mate.album30.domain.orderMatch.entity.Match;
import com.mate.album30.domain.orderMatch.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumTalkService {
    private final ChatRepository chatRepository;  // Chat 데이터를 처리할 Repository
    private final ChatRoomRepository chatRoomRepository;  // ChatRoom 데이터를 처리할 Repository


    public ChatResponseDto.ChatRoomResponseDto getChatRoomInfo(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));

        return ChatResponseDto.ChatRoomResponseDto.convertToChatRoomDto(room);
    }
    public void createChatRoomAfterMatch(Match match) {
        // match를 저장함 -> 추후 멤버 정보가 필요하면 match에서 꺼내서 사용.
        ChatRoom chatRoom = ChatRoom.builder()
                .match(match)
                .build();

        // 채팅방을 생성 함 -> 저장함
        chatRoomRepository.save(chatRoom);
        match.setChatRoom(chatRoom);

    }
    @Transactional
    public void changeStatusOrderAndChatRoom( Long chatRoomId,String orderStatus,Long memberId) {
        // chatRoom가져오기
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
        Order buyer = chatRoom.getMatch().getBuyer();
        Order seller = chatRoom.getMatch().getSeller();
        // chatRoom -> match -> ordeer이동 후 역할 별 거래 상태 수정

        if(memberId == buyer.getMember().getMemberId()) {
            changeStatusByOrderRole(orderStatus, buyer);
        } else {changeStatusByOrderRole(orderStatus, seller);}
        // 두 주문자가 모두 완료 버튼을 누르면 -> chatRoom완료로 바뀜
        if(buyer.getOrderStatus() == OrderStatus.COMPLETION && seller.getOrderStatus() == OrderStatus.COMPLETION) {
            chatRoom.setCompleted(true);
        }


    }
    void changeStatusByOrderRole(String orderStatus, Order order) {
        switch (orderStatus) {
            case "COMPLETION" -> {order.setOrderStatus(OrderStatus.COMPLETION);}
            case "CALCELED" -> {order.setOrderStatus(OrderStatus.CALCELED);}

        }

    }

}
