package com.mate.album30.domain.member.service;

import com.mate.album30.domain.member.entity.Member;
import com.mate.album30.domain.member.repository.MemberRepository;
import com.mate.album30.global.apiPayload.code.exception.GeneralException;
import com.mate.album30.global.apiPayload.code.status.ErrorStatus;
import com.mate.album30.global.auth.dto.response.SignUpMemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(SignUpMemberInfoDto signUpUserInfoDto) {
        Optional<Member> existingMember = memberRepository.findByProviderId(signUpUserInfoDto.getProviderId());
        if (existingMember.isPresent()) {
            return existingMember.get().getMemberId();
        } else {
            Member member = Member.builder()
                    .provider(signUpUserInfoDto.getProvider())
                    .providerId(signUpUserInfoDto.getProviderId())
                    .name(signUpUserInfoDto.getName())
                    .email(signUpUserInfoDto.getEmail())
                    .password(passwordEncoder.encode(signUpUserInfoDto.getPassword()))
                    .build();
            Member savedMember = memberRepository.save(member);
            return savedMember.getMemberId();
        }
    }
}
