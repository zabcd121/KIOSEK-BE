package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class UnableToCheckInStatusException extends BusinessLogicException {
    public UnableToCheckInStatusException() {
        super(ErrorCode.CHECKIN_FAIL_UNABLE_STATUS);
    }
}
