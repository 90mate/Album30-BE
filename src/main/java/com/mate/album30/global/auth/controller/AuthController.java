package com.mate.album30.global.auth.controller;

import com.mate.album30.domain.member.entity.enums.Provider;
import com.mate.album30.domain.member.service.MemberService;
import com.mate.album30.global.apiPayload.ApiResponse;
import com.mate.album30.global.apiPayload.code.exception.GeneralException;
import com.mate.album30.global.apiPayload.code.status.ErrorStatus;
import com.mate.album30.global.apiPayload.code.status.SuccessStatus;
import com.mate.album30.global.auth.dto.response.KakaoUserInfoDto;
import com.mate.album30.global.auth.dto.response.LoginDto;
import com.mate.album30.global.auth.dto.response.SignUpMemberInfoDto;
import com.mate.album30.global.auth.jwt.JWTUtil;
import com.mate.album30.global.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthControllerDocs {

    private final WebClientConfig webClientConfig;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @Value("${kakao.client_id}")
    private String clientId;

    @GetMapping("/kakao")
    public Mono<ApiResponse<LoginDto>> kakaoLogin(String code) {
        return webClientConfig.webClient()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("kauth.kakao.com")
                        .port(-1)
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", "http://localhost:8080/auth/kakao")
                        .queryParam("code", code)
                        .build()
                )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(tokenResponse -> {
                    String accessToken = (String) tokenResponse.get("access_token");
                    return webClientConfig.webClient()
                            .post()
                            .uri("https://kapi.kakao.com/v2/user/me")
                            .header("Authorization", "Bearer " + accessToken)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .bodyValue("property_keys=[\"properties.nickname\"]")
                            .retrieve()
                            .bodyToMono(KakaoUserInfoDto.class)
                            .flatMap(userInfo -> {
                                SignUpMemberInfoDto memberInfoDto = SignUpMemberInfoDto.builder()
                                        .name(userInfo.getProperties().getNickname())
                                        .provider(Provider.KAKAO)
                                        .providerId(String.valueOf(userInfo.getId()))
                                        .password("")
                                        .build();

                                Long userId = memberService.signUp(memberInfoDto);

                                String jwtToken = jwtUtil.createJwt(userId, 60 * 60 * 60 * 1000L);

                                LoginDto loginDto = LoginDto.builder()
                                        .accessToken(jwtToken)
                                        .build();

                                return Mono.just(ApiResponse.onSuccess(SuccessStatus.OK, loginDto));
                            });
                })
                .onErrorResume(err -> {
                    if (err instanceof GeneralException) {
                        return Mono.error(err);
                    } else {
                        return Mono.error(GeneralException.of(ErrorStatus.INVALID_TOKEN_ERROR));
                    }
                });
    }
}
