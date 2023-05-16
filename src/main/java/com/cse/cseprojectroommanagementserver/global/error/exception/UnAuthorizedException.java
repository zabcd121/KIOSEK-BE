package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class UnAuthorizedException extends AbstractBusinessLogicException {
    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
