package com.cse.cseprojectroommanagementserver.global.common.dto;

import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseError {
    private String code;
    private String message;

    public ResponseError(ResponseConditionCode conditionCode) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
    }
}
