package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception;

public class ExceedMaxPeriodEnableReservationException  extends RuntimeException{
    public ExceedMaxPeriodEnableReservationException(String message) {
        super(message);
    }
}