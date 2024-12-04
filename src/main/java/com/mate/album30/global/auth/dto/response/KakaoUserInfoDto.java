package com.mate.album30.global.auth.dto.response;

import lombok.Getter;

@Getter
public class KakaoUserInfoDto {
  private Long id;
  private String connected_at;
  private KakaoProperties properties;
//  private KakaoAccount kakao_account;

  @Getter
  public class KakaoProperties {
    private String nickname;
    private String profile_image;
    private String email;
  }

  /*@Getter
  public class KakaoAccount {
    private String email;
  }*/
}
