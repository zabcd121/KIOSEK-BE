package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api.AdminReservationPolicyApiController;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.NotExistsReservationPolicyException;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.error.ErrorCode.*;

@RestControllerAdvice(assignableTypes = {AdminReservationPolicyApiController.class})
@Slf4j
public class ReservationPolicyControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistsReservationPolicyExHandler(NotExistsReservationPolicyException ex) {
        log.error("[exceptionHandler] NotExistsReservationPolicyException", ex);
        return ErrorResponse.toResponseEntity(RESERVATION_POLICY_SEARCH_FAIL);
    }
}
