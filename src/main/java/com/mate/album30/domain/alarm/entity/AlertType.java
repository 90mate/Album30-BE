package com.mate.album30.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AlertType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long alertTypeId;

    private String name;

    private String description;

    @Enumerated
    private AlertCategory category;

    @OneToMany(mappedBy = "alertType", cascade = CascadeType.ALL)
    private List<Notification> notificationList = new ArrayList<>();

}
