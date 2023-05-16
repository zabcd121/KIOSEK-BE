package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.PolicyInfractionException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxHourPerOnce {

    @Column(nullable = false)
    private Integer maxHour;

    public boolean checkPolicy(LocalDateTime reservationStartAt, LocalDateTime reservationEndAt) {
        Duration duration = Duration.between(reservationStartAt, reservationEndAt);
        long timeToAttempt = duration.toHours();

        if (this.maxHour < timeToAttempt) {
            throw new PolicyInfractionException(ErrorCode.MAX_TIME_LIMIT_POLICY_INFRACTION);
        }
        return true;
    }
}
