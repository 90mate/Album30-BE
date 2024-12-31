package com.mate.album30.domain.member.repository;

import com.mate.album30.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderId(String providerId);
    Member findMemberByMemberId(Long providerId);
}
