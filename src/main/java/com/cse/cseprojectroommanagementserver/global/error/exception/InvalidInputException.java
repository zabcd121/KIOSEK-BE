package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class InvalidInputException extends AbstractErrorException {
    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode);
    }
}
