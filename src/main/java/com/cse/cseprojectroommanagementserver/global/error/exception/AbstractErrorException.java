package com.cse.cseprojectroommanagementserver.global.error.exception;

import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException{
    private final ErrorCode errorCode;

    public AbstractErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
