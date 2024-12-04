package com.mate.album30.global.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequestDto(
	@Schema(description = "id")
	String id,
	@Schema(description = "password")
	String password
) {
}
