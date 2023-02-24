package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

public class ReservationResDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SearchReservationRes {

        private Long projectTableId;
        private String tableName;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedAt;

        public SearchReservationRes of(Reservation reservation) {
            this.projectTableId = reservation.getProjectTable().getTableId();
            this.tableName = reservation.getProjectTable().getTableName();
            this.startAt = reservation.getStartAt();
            this.endAt = reservation.getEndAt();
            if(reservation.getTableReturn() != null) {
                this.returnedAt = reservation.getTableReturn().getReturnedDateTime();
            }

            return this;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class CurrentReservationByMemberRes {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedAt;

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
    public static class PastReservationByMemberRes {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedAt;

        private ReservationStatus reservationStatus;

        private String roomName;

        private String tableName;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SearchReservationByPagingRes {
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
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        private ReservationStatus reservationStatus;

    }






}
