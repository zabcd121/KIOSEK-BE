package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.InvalidInputException;

public class WrongReservationRequestException extends InvalidInputException {
    public WrongReservationRequestException() {
        super(ErrorCode.RESERVE_FAIL_ENDAT_BEFORE_STARTAT);
    }
}
