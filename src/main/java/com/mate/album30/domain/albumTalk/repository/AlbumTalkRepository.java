package com.mate.album30.domain.albumTalk.repository;

import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumTalkRepository {
    // MongoDB에서 AlbumTalk 엔티티와 관련된 작업을 자동으로 처리
}

