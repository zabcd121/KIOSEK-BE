package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class TokenException extends AbstractBusinessLogicException {
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
