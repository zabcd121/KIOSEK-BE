package com.cse.cseprojectroommanagementserver.domain.penalty.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class AlreadyStoppedAccountUntilRequestDateException extends BusinessLogicException {
    public AlreadyStoppedAccountUntilRequestDateException() {
        super(ErrorCode.PENALTY_IMPOSE_FAIL_ALREADY_SUSPENDED_UNTIL_REQUEST_DATETIME);
    }
}
