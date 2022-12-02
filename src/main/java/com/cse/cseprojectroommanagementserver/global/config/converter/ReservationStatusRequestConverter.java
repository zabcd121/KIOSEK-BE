package com.cse.cseprojectroommanagementserver.global.config.converter;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import org.springframework.core.convert.converter.Converter;


public class ReservationStatusRequestConverter implements Converter<String, ReservationStatus> {
    @Override
    public ReservationStatus convert(String reservationStatusCode) {
        return ReservationStatus.ofCode(reservationStatusCode);
    }
}
