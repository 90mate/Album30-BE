package com.mate.album30.domain.albumTalk.repository;

import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
