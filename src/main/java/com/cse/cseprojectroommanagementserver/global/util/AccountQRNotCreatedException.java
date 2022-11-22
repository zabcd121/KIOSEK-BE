package com.cse.cseprojectroommanagementserver.global.util;

import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;

public class AccountQRNotCreatedException extends RuntimeException{
    public AccountQRNotCreatedException(ResponseConditionCode code) {
        super(code.getMessage());
    }
}
