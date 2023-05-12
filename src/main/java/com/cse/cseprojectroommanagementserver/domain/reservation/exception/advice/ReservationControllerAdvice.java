package com.cse.cseprojectroommanagementserver.domain.reservation.exception.advice;

import com.cse.cseprojectroommanagementserver.domain.member.exception.InvalidAccountQRException;
import com.cse.cseprojectroommanagementserver.domain.reservation.api.ReservationApiController;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.*;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxPeriodEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxTimeEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import com.cse.cseprojectroommanagementserver.global.dto.ErrorResponse;
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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> penaltyUserExHandler(StoppedAccountException ex) {
        log.error("[exceptionHandler] PenaltyMemberReserveFailException", ex);
        return ErrorResponse.toResponseEntity((RESERVE_FAIL_PENALTY_USER));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> duplicatedReservationExHandler(DuplicatedReservationException ex) {
        log.error("[exceptionHandler] DuplicatedReservationException", ex);
        return ErrorResponse.toResponseEntity((RESERVE_FAIL_DUPLICATED));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceedTodaysMaxCountExHandler(ExceedTodaysMaxCountEnableReservationException ex) {
        log.error("[exceptionHandler] ExceedTodaysMaxCountEnableReservationException", ex);
        return ErrorResponse.toResponseEntity((RESERVE_FAIL_EXCEED_MAX_COUNT));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceedMaxTimeExHandler(ExceedMaxTimeEnableReservationException ex) {
        log.error("[exceptionHandler] ExceedMaxTimeEnableReservationException", ex);
        return ErrorResponse.toResponseEntity((RESERVE_FAIL_EXCEED_MAX_TIME));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceedTodaysMaxCountExHandler(ExceedMaxPeriodEnableReservationException ex) {
        log.error("[exceptionHandler] ExceedMaxPeriodEnableReservationException", ex);
        return ErrorResponse.toResponseEntity((RESERVE_FAIL_EXCEED_MAX_PERIOD));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notCreatedAccountQRExHandler(ReservationQRNotCreatedException ex) {
        log.error("[exceptionHandler] ReservationQRNotCreatedException", ex);
        return ErrorResponse.toResponseEntity((RESERVATION_QR_CREATE_FAIL));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notExistsReservationExHandler(NotExistsReservationException ex) {
        log.error("[exceptionHandler] NotExistsReservationException", ex);
        return ErrorResponse.toResponseEntity((RESERVATION_SEARCH_FAIL));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> invalidAccountQRExHandler(InvalidAccountQRException ex) {
        log.error("[exceptionHandler] InvalidAccountQRException", ex);
        return ErrorResponse.toResponseEntity((ONSITE_RESERVE_FAIL_ACCOUNT_QR_INVALID));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> invalidReservationQRExHandler(InvalidReservationQRException ex) {
        log.error("[exceptionHandler] InvalidReservationQRException", ex);
        return ErrorResponse.toResponseEntity((CHECKIN_FAIL));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unableToCheckInStatusExHandler(UnableToCheckInStatusException ex) {
        log.error("[exceptionHandler] UnableToCheckInStatusException", ex);
        return ErrorResponse.toResponseEntity((CHECKIN_FAIL_UNABLE_STATUS_TO_CHECKIN));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unableToCheckInTimeExHandler(UnableToCheckInTimeException ex) {
        log.error("[exceptionHandler] UnableToCheckInTimeException", ex);
        return ErrorResponse.toResponseEntity((CHECKIN_FAIL_UNABLE_TIME_TO_CHECKIN));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unableToCancelReservationExHandler(UnableToCancelReservationException ex) {
        log.error("[exceptionHandler] UnableToCancelReservationException", ex);
        return ErrorResponse.toResponseEntity((RESERVATION_CANCEL_FAIL));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> endAtIsBeforeStartAtExHandler(InvalidReservationRequestException ex) {
        log.error("[exceptionHandler] EndAtIsBeforeStartAtException", ex);
        return ErrorResponse.toResponseEntity((RESERVE_FAIL_ENDAT_BEFORE_STARTAT));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> disabledTableExHandler(DisabledTableException ex) {
        log.error("[exceptionHandler] DisabledTableException", ex);
        return ErrorResponse.toResponseEntity((RESERVATION_FAIL_DISABLED_TABLE));
    }
}

