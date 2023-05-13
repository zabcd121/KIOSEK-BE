package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ViolationCountToImposePenalty {

    @Column(nullable = false)
    private Integer countOfViolationsToImposePenalty;

    public boolean isPenaltyTarget(int countOfViolations) {
        if(this.countOfViolationsToImposePenalty <= countOfViolations) {
            return true;
        }
        return false;
    }
}
