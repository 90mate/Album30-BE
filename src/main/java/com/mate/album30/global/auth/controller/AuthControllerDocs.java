package com.mate.album30.global.auth.controller;

import com.mate.album30.global.apiPayload.ApiResponse;
import com.mate.album30.global.auth.dto.response.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface AuthControllerDocs {

    @Operation(
            summary = "Kakao Login",
            parameters = {
                    @Parameter(name = "code", description = "kakao 인가코드를 보냅니다.", required = true)
            })
    public Mono<ApiResponse<LoginDto>> kakaoLogin(@RequestParam(value = "code") String token);

}
