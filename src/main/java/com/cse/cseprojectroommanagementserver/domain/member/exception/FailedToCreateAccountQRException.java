package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.ServerInternalException;

public class FailedToCreateAccountQRException extends ServerInternalException {
    public FailedToCreateAccountQRException() {
        super(ErrorCode.SIGNUP_FAIL_ACCOUNT_QR_CREATE_FAIL);
    }
}
