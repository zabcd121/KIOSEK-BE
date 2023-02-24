package com.cse.cseprojectroommanagementserver.global.common.dto;

import com.cse.cseprojectroommanagementserver.global.common.ResConditionCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResponseError {
    private String code;
    private String message;

    public ResponseError(ResConditionCode conditionCode) {
        this.code = conditionCode.getCode();
        this.message = conditionCode.getMessage();
    }

    public ResponseError(ResConditionCode conditionCode, String message) {
        this.code = conditionCode.getCode();
        this.message = message;
    }
}
