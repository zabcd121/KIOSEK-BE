package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public abstract class BusinessLogicException extends AbstractErrorException{
    public BusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
