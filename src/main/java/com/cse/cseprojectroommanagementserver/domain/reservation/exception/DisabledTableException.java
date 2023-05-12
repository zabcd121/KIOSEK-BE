package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class DisabledTableException extends BusinessLogicException {
    public DisabledTableException() {
        super(ErrorCode.RESERVATION_FAIL_DISABLED_TABLE);
    }
}
