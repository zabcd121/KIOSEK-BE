package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViolationCountToImposePenalty {
    private Integer countOfViolationsToImposePenalty;

    public boolean isPenaltyTarget(int countOfViolations) {
        if(this.countOfViolationsToImposePenalty <= countOfViolations) {
            return true;
        }
        return false;
    }
}
