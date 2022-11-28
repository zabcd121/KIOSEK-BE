package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception;

public class ExceedMaxTimeEnableReservationException extends RuntimeException{
    public ExceedMaxTimeEnableReservationException(String message) {
        super(message);
    }
}
