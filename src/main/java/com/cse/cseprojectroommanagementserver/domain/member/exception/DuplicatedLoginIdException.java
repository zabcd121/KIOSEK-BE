package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class DuplicatedLoginIdException extends BusinessLogicException {
    public DuplicatedLoginIdException() {
        super(ErrorCode.LOGIN_ID_DUPLICATION_CHECK_FAIL_UNUSABLE);
    }
}
