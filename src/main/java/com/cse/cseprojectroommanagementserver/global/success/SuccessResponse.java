package com.cse.cseprojectroommanagementserver.global.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SuccessResponse<T> {
    private String code;
    private String message;
    private T result;

    public SuccessResponse(SuccessCode conditionCode, T result) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
        this.result = result;
    }
}