package com.cse.cseprojectroommanagementserver.domain.reservation.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.member.exception.WrongAccountQRException;
import com.cse.cseprojectroommanagementserver.domain.reservation.api.ReservationApiController;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.*;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxPeriodEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxTimeEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import com.cse.cseprojectroommanagementserver.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.error.ErrorCode.*;

@RestControllerAdvice(assignableTypes = {ReservationApiController.class})
@Slf4j
public class ReservationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> emptyArgument(MethodArgumentNotValidException ex) {
        return makeErrorResponse(ex.getBindingResult());
    }

    private ResponseEntity<ErrorResponse> makeErrorResponse(BindingResult bindingResult) {
        ResponseEntity<ErrorResponse> errorResponse = null;

        if (bindingResult.hasErrors()) {
            String bindResultField = bindingResult.getFieldError().getField();
            log.error("bindResultField: {}", bindResultField);

            switch (bindResultField) {
                case "startDateTime":
                    errorResponse = ErrorResponse.toResponseEntity((RESERVE_FAIL_START_TIME_EMPTY));
                    break;
                case "endDateTime":
                    errorResponse = ErrorResponse.toResponseEntity((RESERVE_FAIL_END_TIME_EMPTY));
                    break;
            }
        }

        return errorResponse;
    }
}

