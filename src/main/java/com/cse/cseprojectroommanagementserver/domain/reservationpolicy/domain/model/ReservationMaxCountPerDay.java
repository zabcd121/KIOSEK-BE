package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxCountPerDay {
    private Integer maxCountPerDay;

    public boolean checkPolicy(Long countTodayMemberCreatedReservation) {
        if(maxCountPerDay <= countTodayMemberCreatedReservation) {
            throw new ExceedTodaysMaxCountEnableReservationException("하루에 최대 예약가능한 " + maxCountPerDay +  "횟수를 초과하였습니다.");
        }
        return true;
    }
}
