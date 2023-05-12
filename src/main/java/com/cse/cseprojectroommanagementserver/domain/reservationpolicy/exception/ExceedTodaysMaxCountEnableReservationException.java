package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class ExceedTodaysMaxCountEnableReservationException extends BusinessLogicException {
    public ExceedTodaysMaxCountEnableReservationException() {
        super(ErrorCode.RESERVE_FAIL_EXCEED_MAX_COUNT);
    }
}