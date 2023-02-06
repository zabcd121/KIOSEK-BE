package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.api.TableDeactivationApiController;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.exception.DuplicatedDeactivationException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.TABLE_DEACTIVATE_DUPLICATED;

@RestControllerAdvice(assignableTypes = {TableDeactivationApiController.class})
@Slf4j
public class TableDeactivationControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError duplicatedReservationExHandler(DuplicatedDeactivationException ex) {
        log.error("[exceptionHandler] DuplicatedDeactivationException", ex);
        return new ResponseError(TABLE_DEACTIVATE_DUPLICATED);
    }
}
