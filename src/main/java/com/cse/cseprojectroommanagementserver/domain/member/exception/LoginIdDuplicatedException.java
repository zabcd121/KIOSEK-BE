package com.cse.cseprojectroommanagementserver.domain.member.exception;

import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;

public class LoginIdDuplicatedException extends RuntimeException{
    public LoginIdDuplicatedException(ResponseConditionCode code) {
        super(code.getMessage());
    }
}
