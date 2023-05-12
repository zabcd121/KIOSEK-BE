package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.api.AdminTableDeactivationApiController;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.exception.DuplicatedDeactivationException;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.dto.ResConditionCode.TABLE_DEACTIVATE_DUPLICATED;

@RestControllerAdvice(assignableTypes = {AdminTableDeactivationApiController.class})
@Slf4j
public class TableDeactivationControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicatedReservationExHandler(DuplicatedDeactivationException ex) {
        log.error("[exceptionHandler] DuplicatedDeactivationException", ex);
        return ErrorResponse.toResponseEntity(TABLE_DEACTIVATE_DUPLICATED);
    }
}
