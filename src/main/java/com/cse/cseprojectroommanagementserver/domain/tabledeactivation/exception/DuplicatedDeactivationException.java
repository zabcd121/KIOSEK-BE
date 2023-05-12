package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class DuplicatedDeactivationException extends BusinessLogicException {
    public DuplicatedDeactivationException() {
        super(ErrorCode.TABLE_DEACTIVATE_FAIL_DUPLICATED);
    }
}
