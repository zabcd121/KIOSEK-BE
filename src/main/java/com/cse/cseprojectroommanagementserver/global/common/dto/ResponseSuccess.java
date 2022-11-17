package com.cse.cseprojectroommanagementserver.global.common.dto;

import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseSuccess<T> {
    private String code;
    private String message;
    private T result;

    public ResponseSuccess(ResponseConditionCode conditionCode, T result) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
        this.result = result;
    }
}