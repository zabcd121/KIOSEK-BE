package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.api.AdminPenaltyPolicyApiController;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception.NotExistsPenaltyPolicyException;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.dto.ResConditionCode.PENALTY_POLICY_SEARCH_FAIL;

@RestControllerAdvice(assignableTypes = {AdminPenaltyPolicyApiController.class})
@Slf4j
public class PenaltyPolicyControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistsPenaltyPolicyExHandler(NotExistsPenaltyPolicyException ex) {
        log.error("[exceptionHandler] NotExistsPenaltyPolicyException", ex);
        return ErrorResponse.toResponseEntity(PENALTY_POLICY_SEARCH_FAIL);
    }

}
