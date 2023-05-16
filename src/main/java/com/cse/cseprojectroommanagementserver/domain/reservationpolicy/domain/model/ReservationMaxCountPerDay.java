package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.PolicyInfractionException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationMaxCountPerDay {

    @Column(nullable = false)
    private Integer maxCount;

    public boolean checkPolicy(Long countTodayMemberCreatedReservation) {
        if(maxCount <= countTodayMemberCreatedReservation) {
            throw new PolicyInfractionException(ErrorCode.MAX_COUNT_LIMIT_POLICY_INFRACTION);
        }
        return true;
    }
}
