package com.cse.cseprojectroommanagementserver.domain.penalty.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.penalty.api.AdminPenaltyApiController;
import com.cse.cseprojectroommanagementserver.domain.penalty.api.PenaltyApiController;
import com.cse.cseprojectroommanagementserver.domain.penalty.exception.ImpossibleExtensionReqException;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.dto.ResConditionCode.PENALTY_IMPOSITION_FAIL;

@RestControllerAdvice(assignableTypes = {AdminPenaltyApiController.class, PenaltyApiController.class})
@Slf4j
public class PenaltyControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> impossibleExtensionReqExHandler(ImpossibleExtensionReqException ex) {
        log.error("[exceptionHandler] ImpossibleExtensionReqException", ex);
        return ErrorResponse.toResponseEntity(PENALTY_IMPOSITION_FAIL);
    }
}
