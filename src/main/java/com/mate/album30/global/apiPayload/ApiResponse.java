package com.mate.album30.global.apiPayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mate.album30.global.apiPayload.code.status.ErrorStatus;
import com.mate.album30.global.apiPayload.code.status.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;


    // 성공한 경우 응답 생성
    public static <T> ApiResponse<T> onSuccess(SuccessStatus success, T result){
        return new ApiResponse<>(true, success.getCode() , success.getMessage(), result);
    }

    // 특정한 경우 응답 생성(예외 처리 등)
    public static <T> ApiResponse<T> of(ErrorStatus error, T result){
        return new ApiResponse<>(true, error.getCode() , error.getMessage(), result);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}