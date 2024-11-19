package com.mate.album30.global.apiPayload.code.exception;

import com.mate.album30.global.apiPayload.code.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private final HttpStatus httpStatus;
    private String code;
    private final String message;

    public static GeneralException of(ErrorStatus error) {
        return new GeneralException(error.getHttpStatus(), error.getCode(), error.getMessage());
    }
}