package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class ExpiredRefreshTokenException extends BusinessLogicException {
    public ExpiredRefreshTokenException() {
        super(ErrorCode.TOKEN_REISSUE_FAIL_REFRESH_TOKEN_EXPIRED);
    }
}
