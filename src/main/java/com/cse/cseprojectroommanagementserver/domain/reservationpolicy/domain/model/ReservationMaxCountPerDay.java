package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxCountPerDay {
    private Integer maxCount;

    public boolean checkPolicy(Long countTodayMemberCreatedReservation) {
        if(maxCount <= countTodayMemberCreatedReservation) {
            throw new ExceedTodaysMaxCountEnableReservationException();
        }
        return true;
    }
}
