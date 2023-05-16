package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class PolicyInfractionException extends AbstractBusinessLogicException {
    public PolicyInfractionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
