package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class StoppedAccountException extends BusinessLogicException {
    public StoppedAccountException() {
        super(ErrorCode.RESERVE_FAIL_PENALTY_USER);
    }
}
