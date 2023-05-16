package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public abstract class AbstractBusinessLogicException extends AbstractErrorException{
    public AbstractBusinessLogicException(ErrorCode errorCode) {
        super(errorCode);
    }
}
