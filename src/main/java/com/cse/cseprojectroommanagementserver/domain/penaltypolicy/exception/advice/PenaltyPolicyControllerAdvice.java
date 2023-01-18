package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.api.PenaltyPolicyApiController;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception.NotExistsPenaltyPolicyException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.PENALTY_POLICY_SEARCH_FAIL;

@RestControllerAdvice(assignableTypes = {PenaltyPolicyApiController.class})
@Slf4j
public class PenaltyPolicyControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseError notExistsPenaltyPolicyExHandler(NotExistsPenaltyPolicyException ex) {
        log.error("[exceptionHandler] NotExistsPenaltyPolicyException", ex);
        return new ResponseError(PENALTY_POLICY_SEARCH_FAIL);
    }

}
