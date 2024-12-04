package com.mate.album30.domain.album.repository;

import com.mate.album30.domain.album.entity.Album;
import com.mate.album30.domain.album.entity.FavoriteAlbum;
import com.mate.album30.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteAlbumRepository extends JpaRepository<FavoriteAlbum, Long> {

    Optional<FavoriteAlbum> findByMemberAndAlbum(Member member, Album album);

    List<FavoriteAlbum> findAllByMember(Member member);
}