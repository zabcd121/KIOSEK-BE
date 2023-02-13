package com.cse.cseprojectroommanagementserver.domain.reservation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservation.exception.InvalidReservationStatusCodeException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NullReservationStatusCodeException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ReservationStatus {
    RESERVATION_COMPLETED("01", "예약완료"),
    CANCELED("02", "취소됨"),
    IN_USE("03", "사용중"),
    UN_USED("04", "미사용"),
    RETURN_WAITING("05", "반납대기중"),
    NOT_RETURNED("06", "미반납"),
    RETURNED("07", "반납완료");

    private final String statusCode;
    private final String status;

    public static ReservationStatus ofCode(String inputStatusCode) {
        if(inputStatusCode == null) {
            throw new NullReservationStatusCodeException();
        }

        for (ReservationStatus rs : ReservationStatus.values()) {
            if(rs.statusCode.equals(inputStatusCode)) {
                return rs;
            }
        }
        throw new InvalidReservationStatusCodeException();
    }

    public static ReservationStatus ofStatus(String status) {
        if(status == null) {
            throw new NullReservationStatusCodeException();
        }

        for (ReservationStatus rs : ReservationStatus.values()) {
            if(rs.status.equals(status)) {
                return rs;
            }
        }
        throw new InvalidReservationStatusCodeException();
    }

}


