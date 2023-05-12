package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NotFoundMemberException extends BusinessLogicException {
    public NotFoundMemberException() {
        super(ErrorCode.SEARCH_MEMBER_FAIL_NOT_FOUND);
    }
}
