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
        /**
         *
         *
         * */
        Long chatRoomId;
        OrderStatus buyerStatus;
        OrderStatus sellerStatus;
        LocalDateTime updatedAt;
        String recentChat;
        Long buyerId;
        Long sellerId;
        String sellerNickName;


        /**
         * 쿼리 필요
         * */
        Long orderId;
        DeliveryType deliveryType;
        String group;
        String artist;
        String image;

        public static ChatRoomResponseDto convertToChatRoomDto(ChatRoom room) {
            return ChatRoomResponseDto.builder()
                    .chatRoomId(room.getChatRoomId())
//                    .buyerStatus(room.getBuyerStatus())
//                    .sellerStatus(room.getSellerStatus())
                    .updatedAt(room.getUpdatedAt())
//                    .recentChat(room.getChats().get)
                    .buyerId(room.getBuyer().getMemberId())
                    .sellerId(room.getSeller().getMemberId())
                    .sellerNickName(room.getSeller().getNickName())

                    .orderId(room.getOrderId())
//                    .deliveryType(room.getOrderId().deleveryType)
//                    .group(room.)
//                    .artist()
//                    .image(room.getOrderId().image)

                    .build();
        }

    }
}














