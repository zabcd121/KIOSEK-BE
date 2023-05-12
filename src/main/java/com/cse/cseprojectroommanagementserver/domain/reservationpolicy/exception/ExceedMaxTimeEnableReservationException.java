package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class ExceedMaxTimeEnableReservationException extends BusinessLogicException {
    public ExceedMaxTimeEnableReservationException() {
        super(ErrorCode.RESERVE_FAIL_EXCEED_MAX_TIME);
    }
}
