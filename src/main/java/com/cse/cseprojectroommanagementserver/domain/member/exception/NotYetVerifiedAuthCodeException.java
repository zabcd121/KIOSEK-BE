package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NotYetVerifiedAuthCodeException extends BusinessLogicException {
    public NotYetVerifiedAuthCodeException() {
        super(ErrorCode.AUTH_CODE_NOT_YET_VERIFIED);
    }
}
