package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class UnableToCheckInTimeException extends BusinessLogicException {
    public UnableToCheckInTimeException() {
        super(ErrorCode.CHECKIN_FAIL_UNABLE_TIME_TO_CHECKIN);
    }
}
