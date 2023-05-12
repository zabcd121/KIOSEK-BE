package com.cse.cseprojectroommanagementserver.global.error;

import lombok.*;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        ErrorResponse e = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(e, errorCode.getHttpStatus());
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
