package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public abstract class ServerInternalException extends AbstractErrorException{
    public ServerInternalException(ErrorCode errorCode) {
        super(errorCode);
    }
}
