package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class DuplicatedEmailException extends BusinessLogicException {
    public DuplicatedEmailException() {
        super(ErrorCode.EMAIL_DUPLICATION_CHECK_FAIL_UNUSABLE);
    }

}
