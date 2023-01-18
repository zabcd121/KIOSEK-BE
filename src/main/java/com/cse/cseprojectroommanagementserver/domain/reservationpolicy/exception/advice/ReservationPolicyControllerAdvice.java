package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api.ReservationPolicyApiController;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.NotExistsReservationPolicyException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.RESERVATION_POLICY_SEARCH_FAIL;

@RestControllerAdvice(assignableTypes = {ReservationPolicyApiController.class})
@Slf4j
public class ReservationPolicyControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError notExistsReservationPolicyExHandler(NotExistsReservationPolicyException ex) {
        log.error("[exceptionHandler] NotExistsReservationPolicyException", ex);
        return new ResponseError(RESERVATION_POLICY_SEARCH_FAIL);
    }
}
