package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.PolicyInfractionException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxPeriod {

    @Column(nullable = false)
    private Integer maxPeriod; //week

    /**
     * 최대 주기가 2주라면 오늘을 제외한 2주 뒤까지 예약이 활성화 된다.
     * 날짜별로 예약이 당일 08시부터 익일 08시까지 가능하기 때문에
     * 최대주기가 1주이고 오늘이 1일이라면 8일까지 예약이 가능한건데 8일에는 9일 08시까지 예약이 가능하므로 실질적으로 1일부터 9일 08시까지 예약이 가능하다.
     * @param reservationEndDateTime
     * @return boolean
     */
    public boolean checkPolicy(LocalDateTime reservationEndDateTime) {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime maxAvailableDateTimeForReservation = LocalDateTime.of(current.toLocalDate().plusDays(maxPeriod *7+1), LocalTime.of(8, 0));

        if(reservationEndDateTime.isAfter(maxAvailableDateTimeForReservation)) {
            throw new PolicyInfractionException(ErrorCode.MAX_PERIOD_LIMIT_POLICY_INFRACTION);
        }
        return true;
    }

}
