package com.cse.cseprojectroommanagementserver.domain.penalty.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.penalty.api.AdminPenaltyApiController;
import com.cse.cseprojectroommanagementserver.domain.penalty.api.PenaltyApiController;
import com.cse.cseprojectroommanagementserver.domain.penalty.exception.ImpossibleExtensionReqException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.PENALTY_IMPOSITION_FAIL;

@RestControllerAdvice(assignableTypes = {AdminPenaltyApiController.class, PenaltyApiController.class})
@Slf4j
public class PenaltyControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError impossibleExtensionReqExHandler(ImpossibleExtensionReqException ex) {
        log.error("[exceptionHandler] ImpossibleExtensionReqException", ex);
        return new ResponseError(PENALTY_IMPOSITION_FAIL);
    }
}
