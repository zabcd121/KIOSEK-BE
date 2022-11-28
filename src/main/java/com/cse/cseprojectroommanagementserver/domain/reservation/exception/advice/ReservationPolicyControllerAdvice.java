package com.cse.cseprojectroommanagementserver.domain.reservation.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.reservation.api.ReservationApiController;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.DuplicatedReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.PenaltyMemberReserveFailException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxPeriodEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxTimeEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestControllerAdvice(assignableTypes = {ReservationApiController.class})
@Slf4j
public class ReservationPolicyControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError emptyArgument(MethodArgumentNotValidException ex) {
        ResponseError responseError = makeErrorResponse(ex.getBindingResult());
        return responseError;
    }

    private ResponseError makeErrorResponse(BindingResult bindingResult) {
        ResponseError responseError = null;

        if (bindingResult.hasErrors()) {
            String bindResultField = bindingResult.getFieldError().getField();
            log.error("bindResultField: {}", bindResultField);

            switch (bindResultField) {
                case "startDateTime":
                    responseError = new ResponseError(RESERVATION_START_TIME_EMPTY);
                    break;
                case "endDateTime":
                    responseError = new ResponseError(RESERVATION_END_TIME_EMPTY);
                    break;
            }
        }

        return responseError;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseError penaltyUserExHandler(PenaltyMemberReserveFailException ex) {
        log.error("[exceptionHandler] PenaltyMemberReserveFailException", ex);
        return new ResponseError(RESERVATION_FAIL_PENALTY_USER);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError duplicatedReservationExHandler(DuplicatedReservationException ex) {
        log.error("[exceptionHandler] DuplicatedReservationException", ex);
        return new ResponseError(RESERVATION_FAIL_RESERVATION_DUPLICATED);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError exceedTodaysMaxCountExHandler(ExceedTodaysMaxCountEnableReservationException ex) {
        log.error("[exceptionHandler] ExceedTodaysMaxCountEnableReservationException", ex);
        return new ResponseError(RESERVATION_FAIL_EXCEED_MAX_COUNT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError exceedMaxTimeExHandler(ExceedMaxTimeEnableReservationException ex) {
        log.error("[exceptionHandler] ExceedMaxTimeEnableReservationException", ex);
        return new ResponseError(RESERVATION_FAIL_EXCEED_MAX_TIME);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseError exceedTodaysMaxCountExHandler(ExceedMaxPeriodEnableReservationException ex) {
        log.error("[exceptionHandler] ExceedMaxPeriodEnableReservationException", ex);
        return new ResponseError(RESERVATION_FAIL_EXCEED_MAX_PERIOD);
    }


}

