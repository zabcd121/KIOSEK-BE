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
import static com.cse.cseprojectroommanagementserver.global.formatter.DateFormatProvider.*;

public class ReservationResDto {

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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservedAndTableDeactivationInfoRes {
        List<ReservationSearchRes> reservedList;
        List<TableDeactivationSearchRes> tableDeactivationList;

        public static ReservedAndTableDeactivationInfoRes of(List<ReservationSearchRes> reservedList, List<TableDeactivationSearchRes> tableDeactivationList) {
            return new ReservedAndTableDeactivationInfoRes(reservedList, tableDeactivationList);
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
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

        public static ReservationSearchRes of(Reservation reservation) {
            return ReservationSearchRes.builder()
                    .projectTableId(reservation.getProjectTable().getTableId())
                    .tableName(reservation.getProjectTable().getTableName())
                    .startAt(reservation.getStartAt())
                    .endAt(reservation.getEndAt())
                    .returnedAt(reservation.getTableReturn() != null ? reservation.getTableReturn().getReturnedAt() : null)
                    .reservationStatus(reservation.getReservationStatus())
                    .build();
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

    @NoArgsConstructor
    @Getter
    public static class SearchReservationByPagingRes {
        private ReservationSimpleInfoRes reservation;

        private TableReturnSimpleInfoRes tableReturn;

        private MemberSimpleInfoRes member;
    }


}
