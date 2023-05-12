package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class ExpiredAuthCodeException extends BusinessLogicException {
    public ExpiredAuthCodeException() {
        super(ErrorCode.AUTH_CODE_VERIFY_FAIL_EXPIRED);
    }
}
