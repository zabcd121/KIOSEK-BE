package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class ExpiredException extends AbstractBusinessLogicException {
    public ExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
