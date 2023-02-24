package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxTimeEnableReservationException;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxHourPerOnce {

    private Integer maxHour;

    public boolean checkPolicy(LocalDateTime reservationStartAt, LocalDateTime reservationEndAt) {
        Duration duration = Duration.between(reservationStartAt, reservationEndAt);
        long timeToAttempt = duration.toHours();

        if (this.maxHour < timeToAttempt) {
            throw new ExceedMaxTimeEnableReservationException("한번에 최대 예약가능한 " + this.maxHour +  "시간을 초과하였습니다.");
        }
        return true;
    }
}
