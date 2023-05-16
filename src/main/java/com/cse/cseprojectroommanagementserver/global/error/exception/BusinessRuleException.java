package com.cse.cseprojectroommanagementserver.global.error.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;

public class BusinessRuleException extends AbstractBusinessLogicException {
    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
