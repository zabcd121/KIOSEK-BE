package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.ServerInternalException;

public class FailedToCreateReservationQRException extends ServerInternalException {
    public FailedToCreateReservationQRException() {
        super(ErrorCode.RESERVE_FAIL_RESERVATION_QR_CREATE_FAIL);
    }
}
