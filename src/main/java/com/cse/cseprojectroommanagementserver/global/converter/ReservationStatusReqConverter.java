package com.cse.cseprojectroommanagementserver.global.converter;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import org.springframework.core.convert.converter.Converter;


public class ReservationStatusReqConverter implements Converter<String, ReservationStatus> {
    @Override
    public ReservationStatus convert(String reservationStatusCode) {
        return ReservationStatus.ofCode(reservationStatusCode);
    }
}
