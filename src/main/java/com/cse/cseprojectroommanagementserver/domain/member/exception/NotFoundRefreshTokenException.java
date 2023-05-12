package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NotFoundRefreshTokenException extends BusinessLogicException {
    public NotFoundRefreshTokenException() {
        super(ErrorCode.TOKEN_REISSUE_FAIL_REFRESH_TOKEN_NOT_FOUND_IN_STORE);
    }
}
