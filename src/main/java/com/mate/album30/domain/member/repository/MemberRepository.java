package com.mate.album30.domain.member.repository;

import com.mate.album30.domain.member.entity.Member;
import com.mate.album30.domain.member.entity.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findMemberByMemberId(Long memberId);

    boolean existsWithdrawnMemberByMemberId(Long memberId);
}
