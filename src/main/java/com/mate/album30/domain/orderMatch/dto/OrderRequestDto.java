package com.mate.album30.domain.orderMatch.dto;

import com.mate.album30.domain.common.enums.DeliveryType;
import com.mate.album30.domain.common.enums.OrderStatus;
import com.mate.album30.domain.common.enums.Role;
import com.mate.album30.domain.orderMatch.entity.enums.Components;
import lombok.Data;

@Data
public class OrderRequestDto {
    private OrderStatus orderStatus;
    private Integer quantity;
    private Components components;
    private Integer price;
    private DeliveryType deliveryType;
    private Integer period;
    private Role role;
    private Long albumId; // Album의 ID
    private Long memberId; // Member의 ID
}
