package com.cse.cseprojectroommanagementserver.global.dto;

import lombok.*;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class ErrorResponse {
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResConditionCode errorCode) {
        ErrorResponse e = ErrorResponse.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build();
        return new ResponseEntity<>(e, errorCode.getHttpStatus());
    }

    public static ErrorResponse of(ResConditionCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
