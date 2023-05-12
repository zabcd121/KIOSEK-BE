package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class DuplicatedReservationException  extends BusinessLogicException {
    public DuplicatedReservationException() {
        super(ErrorCode.RESERVE_FAIL_DUPLICATED);
    }
}