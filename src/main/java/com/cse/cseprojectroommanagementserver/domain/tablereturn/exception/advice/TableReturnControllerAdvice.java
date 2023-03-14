package com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.api.TableReturnApiController;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.UnableToReturnAnotherUserReservationException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.RESERVATION_SEARCH_FAIL;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.RETURN_FAIL_ANOTHER_USERS_RESERVATION;

@RestControllerAdvice(assignableTypes = {TableReturnApiController.class})
@Slf4j
public class TableReturnControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError notExistsReservationExHandler(NotExistsReservationException ex) {
        log.error("[exceptionHandler] NotExistsReservationException", ex);
        return new ResponseError(RESERVATION_SEARCH_FAIL);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError unableToReturnAnotherUserReservationExHandler(UnableToReturnAnotherUserReservationException ex) {
        log.error("[exceptionHandler] UnableToReturnAnotherUserReservationException", ex);
        return new ResponseError(RETURN_FAIL_ANOTHER_USERS_RESERVATION);
    }
}
