package com.cse.cseprojectroommanagementserver.domain.reservation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;


public class NoAuthorityToCancelException extends BusinessLogicException {
    public NoAuthorityToCancelException() {
        super(ErrorCode.RESERVATION_CANCEL_FAIL_NO_AUTHORITY);
    }
}
