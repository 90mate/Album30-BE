package com.mate.album30.global.auth.dto.response;

import com.mate.album30.domain.member.entity.enums.Provider;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpMemberInfoDto {
  private String name;
  private Provider provider;
  private String providerId;
  private String email;
  private String password;
}
