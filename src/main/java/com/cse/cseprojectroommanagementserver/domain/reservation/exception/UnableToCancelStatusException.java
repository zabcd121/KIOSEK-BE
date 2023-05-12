package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class UnableToCancelStatusException extends BusinessLogicException {
    public UnableToCancelStatusException() {
        super(ErrorCode.RESERVATION_CANCEL_FAIL_UNABLE_STATUS);
    }
}
