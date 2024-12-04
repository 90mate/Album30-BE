package com.mate.album30.global.auth.controller;

import com.mate.album30.global.apiPayload.ApiResponse;
import com.mate.album30.global.auth.dto.response.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

public interface AuthControllerDocs {

    @Operation(
            summary = "Kakao Login",
            parameters = {
                    @Parameter(name = "Authorization", description = "kakao access token을 보냅니다. Bearer token 형식입니다.", required = true, in = ParameterIn.HEADER)
            })
    public Mono<ApiResponse<LoginDto>> kakaoLogin(@RequestParam(value = "code") String token);

}
