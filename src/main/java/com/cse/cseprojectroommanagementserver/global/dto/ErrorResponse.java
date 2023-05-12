package com.cse.cseprojectroommanagementserver.global.dto;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class ErrorResponse {
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        ErrorResponse e = ErrorResponse.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build();
        return new ResponseEntity<>(e, errorCode.getHttpStatus());
    }

    public static ErrorResponse of(SuccessCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
