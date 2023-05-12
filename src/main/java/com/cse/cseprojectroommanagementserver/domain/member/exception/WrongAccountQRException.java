package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class WrongAccountQRException extends BusinessLogicException {
    public WrongAccountQRException() {
        super(ErrorCode.ONSITE_RESERVE_FAIL_ACCOUNT_QR_WRONG);
    }
}
