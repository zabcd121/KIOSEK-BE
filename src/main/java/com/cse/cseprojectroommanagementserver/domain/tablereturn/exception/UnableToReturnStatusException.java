package com.cse.cseprojectroommanagementserver.domain.tablereturn.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class UnableToReturnStatusException extends BusinessLogicException {
    public UnableToReturnStatusException() {
        super(ErrorCode.RETURN_FAIL_UNABLE_STATUS);
    }
}
