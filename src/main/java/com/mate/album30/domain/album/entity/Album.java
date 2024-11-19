package com.mate.album30.domain.album.entity;

import com.mate.album30.domain.album.entity.enums.Category;
import com.mate.album30.domain.artist.entity.Group;
import com.mate.album30.domain.common.BaseEntity;
import com.mate.album30.domain.orderMatch.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class Album extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long albumId;

    @Column(nullable = false)
    private String albumName;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Boolean isOpend;

    @Column(nullable = false)
    private String coverImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
