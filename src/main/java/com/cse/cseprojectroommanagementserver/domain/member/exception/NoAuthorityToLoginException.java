package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NoAuthorityToLoginException extends BusinessLogicException {
    public NoAuthorityToLoginException() {
        super(ErrorCode.ACCESS_FAIL_NO_AUTHORITY);
    }
}
