package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception;


import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessLogicException;

public class NotFoundPenaltyPolicyException extends BusinessLogicException {
    public NotFoundPenaltyPolicyException() {
        super(ErrorCode.PENALTY_POLICY_SEARCH_FAIL_NOT_FOUND);
    }
}
