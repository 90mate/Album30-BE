package com.mate.album30.global.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpRequestDto(
	@Schema(description = "회원 id")
	String id,
	@Schema(description = "닉네임")
	String username,
	@Schema(description = "비밀 번호")
	String password,
	@Schema(description = "프로필 사진")
	String profile,
	@Schema(description = "email")
	String email
) {
}
