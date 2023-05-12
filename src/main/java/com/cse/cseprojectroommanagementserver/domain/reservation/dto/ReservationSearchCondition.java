package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.global.formatter.DateFormatProvider.LOCAL_DATE_FORMAT;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
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
