package com.cse.cseprojectroommanagementserver.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SuccessResponseNoResult {
    private String code;
    private String message;

    public SuccessResponseNoResult(SuccessCode conditionCode) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
    }
}
