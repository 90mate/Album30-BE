package com.mate.album30.domain.orderMatch.repository;

import com.mate.album30.domain.orderMatch.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Match, Long> {
}

