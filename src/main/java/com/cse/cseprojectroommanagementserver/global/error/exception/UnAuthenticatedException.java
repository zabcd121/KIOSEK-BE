package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class UnAuthenticatedException extends AbstractBusinessLogicException {
    public UnAuthenticatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
