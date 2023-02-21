package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxCountPerDay {
    private Integer value;

    public boolean checkPolicy(Long countTodayMemberCreatedReservation) {
        if(value <= countTodayMemberCreatedReservation) {
            throw new ExceedTodaysMaxCountEnableReservationException("하루에 최대 " + value +  "회 예약 가능합니다.");
        }
        return true;
    }
}
