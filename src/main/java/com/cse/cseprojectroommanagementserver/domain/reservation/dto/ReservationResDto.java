package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnResDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

public class ReservationResDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ReservationSimpleInfoRes {
        private Long reservationId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        private ReservationStatus reservationStatus;

        private String roomName;

        private String tableName;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter @Setter
    public static class ReservationImpossibleSearchRes {
        List<ReservationSearchRes> reservedList;
        List<TableDeactivationSearchRes> tableDeactivationList;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ReservationSearchRes {
        private Long projectTableId;
        private String tableName;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedAt;

        private ReservationStatus reservationStatus;

        public ReservationSearchRes of(Reservation reservation) {
            this.projectTableId = reservation.getProjectTable().getTableId();
            this.tableName = reservation.getProjectTable().getTableName();
            this.startAt = reservation.getStartAt();
            this.endAt = reservation.getEndAt();
            if(reservation.getTableReturn() != null) {
                this.returnedAt = reservation.getTableReturn().getReturnedAt();
            }
            this.reservationStatus = reservation.getReservationStatus();
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
    @Getter @Setter
    public static class SearchReservationByPagingRes {
        private ReservationSimpleInfoRes reservation;

        private TableReturnSimpleInfoRes tableReturn;

        private MemberSimpleInfoRes member;
    }


}
