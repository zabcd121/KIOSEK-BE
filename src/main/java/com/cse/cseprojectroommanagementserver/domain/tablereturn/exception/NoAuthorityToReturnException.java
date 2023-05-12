package com.cse.cseprojectroommanagementserver.domain.tablereturn.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NoAuthorityToReturnException extends BusinessLogicException {
    public NoAuthorityToReturnException() {
        super(ErrorCode.RETURN_FAIL_NO_AUTHORITY);
    }
}
