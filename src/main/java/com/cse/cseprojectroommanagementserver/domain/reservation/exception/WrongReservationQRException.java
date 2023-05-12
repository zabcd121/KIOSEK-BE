package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class WrongReservationQRException extends BusinessLogicException {
    public WrongReservationQRException() {
        super(ErrorCode.CHECKIN_FAIL_WRONG_RESERVATION_QR);
    }
}
