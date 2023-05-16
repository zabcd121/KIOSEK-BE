package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class DuplicationException extends AbstractBusinessLogicException {
    public DuplicationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
