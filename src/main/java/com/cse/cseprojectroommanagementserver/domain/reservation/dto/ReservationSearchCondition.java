package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_FORMAT;

@Data
public class ReservationSearchCondition {
    @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
    private LocalDate startDt;

    @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
    private LocalDate endDt;

    private String memberName;
    private String loginId;
    private String reservationStatus;
    private String roomName;
}
