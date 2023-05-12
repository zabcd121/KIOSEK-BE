package com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.api.TableReturnApiController;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.UnableToReturnException;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.dto.ResConditionCode.*;

@RestControllerAdvice(assignableTypes = {TableReturnApiController.class})
@Slf4j
public class TableReturnControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistsReservationExHandler(NotExistsReservationException ex) {
        log.error("[exceptionHandler] NotExistsReservationException", ex);
        return ErrorResponse.toResponseEntity(RESERVATION_SEARCH_FAIL);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unableToReturnAnotherUserReservationExHandler(UnableToReturnException ex) {
        log.error("[exceptionHandler] UnableToReturnAnotherUserReservationException", ex);
        return ErrorResponse.toResponseEntity(RETURN_FAIL_NO_AUTHORITY);
    }
}
