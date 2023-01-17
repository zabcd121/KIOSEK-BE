package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PenaltyPolicy extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long penaltyPolicyId;

    @Embedded
    private ViolationCountToImposePenalty violationCountToImposePenalty;

    @Embedded
    private NumberOfSuspensionDay numberOfSuspensionDay;

    @Enumerated(EnumType.STRING)
    private AppliedStatus appliedStatus;

    public boolean isPenaltyTarget(int countOfViolations) {
        return this.violationCountToImposePenalty.isPenaltyTarget(countOfViolations);
    }

}
