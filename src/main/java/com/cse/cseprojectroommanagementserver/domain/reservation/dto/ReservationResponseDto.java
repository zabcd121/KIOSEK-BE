package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

public class ReservationResponseDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SearchReservationResponse {

        private Long projectTableId;
        private String tableName;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedDateTime;

        public SearchReservationResponse of(Reservation reservation) {
            this.projectTableId = reservation.getProjectTable().getTableId();
            this.tableName = reservation.getProjectTable().getTableName();
            this.startDateTime = reservation.getStartDateTime();
            this.endDateTime = reservation.getEndDateTime();
            if(reservation.getTableReturn() != null) {
                this.returnedDateTime = reservation.getTableReturn().getReturnedDateTime();
            }

            return this;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class CurrentReservationByMemberResponse {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedDateTime;

        private ReservationStatus reservationStatus;

        private String roomName;

        private String tableName;

        private String imageName;

        private String imageURL;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PastReservationByMemberResponse {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedDateTime;

        private ReservationStatus reservationStatus;

        private String roomName;

        private String tableName;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SearchReservationByPagingResponse {
        private ReservationSimpleInfo reservation;

        private TableReturnSimpleInfo tableReturn;

        private MemberSimpleInfo member;

        private String roomName;

        private String tableName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ReservationSimpleInfo {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;

        private ReservationStatus reservationStatus;

    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableReturnSimpleInfo {
        private Long tableReturnId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedDateTime;

        private Image cleanUpPhoto;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class MemberSimpleInfo {
        private Long memberId;
        private String name;
        private String loginId;
    }


}
