package com.mate.album30.domain.orderMatch.repository;

import com.mate.album30.domain.orderMatch.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.buyer.album.albumId = :albumId OR m.seller.album.albumId = :albumId")
    List<Match> findByAlbumId(@Param("albumId") Long albumId);
}

