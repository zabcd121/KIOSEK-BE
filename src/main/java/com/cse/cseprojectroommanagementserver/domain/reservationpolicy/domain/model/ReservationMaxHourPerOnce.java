package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxTimeEnableReservationException;
import lombok.*;

import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxHourPerOnce {

    private Integer maxHourPerOnce;

    public boolean checkPolicy(LocalDateTime reservationStartDateTime, LocalDateTime reservationEndDateTime) {
        Duration duration = Duration.between(reservationStartDateTime, reservationEndDateTime);
        long timeToAttempt = duration.toHours();

        if (this.maxHourPerOnce < timeToAttempt) {
            throw new ExceedMaxTimeEnableReservationException("한번에 최대 예약가능한 " + maxHourPerOnce +  "시간을 초과하였습니다.");
        }
        return true;
    }
}
