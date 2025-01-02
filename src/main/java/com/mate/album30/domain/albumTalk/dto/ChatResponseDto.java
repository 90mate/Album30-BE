package com.mate.album30.domain.albumTalk.dto;

import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.common.enums.DeliveryType;
import com.mate.album30.domain.common.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class ChatResponseDto {

    @Builder
    @Getter
    public static class ChatRoomResponseDto {

        Long chatRoomId;
        OrderStatus buyerStatus;
        OrderStatus sellerStatus;
        LocalDateTime updatedAt;
        String recentChat;
        String buyer;
        String seller;
        String status;


        /**
         * 쿼리 필요
         * */
        DeliveryType deliveryType;
        String group;
        String artist;
        String image;

        public static ChatRoomResponseDto convertToChatRoomDto(ChatRoom room) {
            String groupName = room.getMatch().getSeller().getAlbum().getGroup().getGroupName();
            return ChatRoomResponseDto.builder()
                    .chatRoomId(room.getChatRoomId())
//                    .buyerStatus(room.getBuyerStatus())
//                    .sellerStatus(room.getSellerStatus())
                    .updatedAt(room.getUpdatedAt())
//                    .recentChat(room.getChats().get)
                    .buyer(room.getMatch().getBuyer().getMember().getNickName())
                    .seller(room.getMatch().getSeller().getMember().getNickName())


//                    .deliveryType(room.getOrderId().deleveryType)
                    .group(groupName != null ? groupName : "NCT 마크(추후 데이터 받아옴)")
                    .artist("/마크(추후 데이터 받아옴)")
//                    .image(room.getOrderId().image)
                    .status(room.isCompleted() ? "거래 완료" : "거래중")

                    .build();
        }

    }
}














