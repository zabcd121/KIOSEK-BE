package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception;

public class ExceedTodaysMaxCountEnableReservationException extends RuntimeException{
    public ExceedTodaysMaxCountEnableReservationException(String message) {
        super(message);
    }
}