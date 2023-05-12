package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NotFoundReservationPolicyException extends BusinessLogicException {
    public NotFoundReservationPolicyException() {
        super(ErrorCode.RESERVATION_POLICY_SEARCH_FAIL);
    }
}
