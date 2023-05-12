package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.InvalidInputException;

public class InvalidReservationStatusCodeException extends InvalidInputException {
    public InvalidReservationStatusCodeException(){
        super(ErrorCode.RESERVATION_STATUS_CONVERT_FAIL);
    }
}
