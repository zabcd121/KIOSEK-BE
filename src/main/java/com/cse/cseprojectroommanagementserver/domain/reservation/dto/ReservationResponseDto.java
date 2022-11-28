package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Enumerated;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

public class ReservationResponseDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SearchReservationResponse {

        private Long projectTableId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedDateTime;
    }

    @NoArgsConstructor
    @Getter
    public static class CurrentReservationByMemberResponse {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;

        private ReservationStatus reservationStatus;

        private String roomName;

        private String tableNo;

        private String imageName;

        private String imageURL;
    }

}
