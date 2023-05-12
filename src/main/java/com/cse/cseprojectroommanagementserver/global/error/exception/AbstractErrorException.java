package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException{
    private final ErrorCode errorCode;

    public AbstractErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
