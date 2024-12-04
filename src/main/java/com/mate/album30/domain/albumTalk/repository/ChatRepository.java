package com.mate.album30.domain.albumTalk.repository;

import com.mate.album30.domain.albumTalk.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    // MongoDB에서 AlbumTalk 엔티티와 관련된 작업을 자동으로 처리



}


