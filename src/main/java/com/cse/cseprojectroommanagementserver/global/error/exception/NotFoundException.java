package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class NotFoundException extends AbstractBusinessLogicException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
