package com.mate.album30.domain.albumTalk.repository;

import com.mate.album30.domain.albumTalk.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.match.buyer.member.memberId = :memberId OR cr.match.seller.member.memberId = :memberId")
    List<ChatRoom> findChatRoomsByMemberId(@Param("memberId") Long memberId);


}
