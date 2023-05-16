package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class IncorrectException extends AbstractBusinessLogicException {
    public IncorrectException(ErrorCode errorCode) {
        super(errorCode);
    }
}
