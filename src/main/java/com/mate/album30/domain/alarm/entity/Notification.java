package com.mate.album30.domain.alarm.entity;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.albumTalk.entity.Chat;
import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.orderMatch.entity.Order;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String title;

    private String description;

    private boolean isAllowed;

    private boolean isRead;

    @ManyToOne
    @Column(name = "alertType")
    private AlertType alertType;
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = true)
//    private Order order; // 선택적 참조
//
//    @ManyToOne
//    @JoinColumn(name = "album_id", nullable = true)
//    private Album album; // 선택적 참조

    @ManyToOne
    @JoinColumn(name = "chatRoom_id", nullable = true)
    private ChatRoom chatRoom; // 선택적 참조

}
