package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NotFoundReservationException extends BusinessLogicException {
    public NotFoundReservationException() {
        super(ErrorCode.RESERVATION_SEARCH_FAIL_NOT_FOUND);
    }
}
