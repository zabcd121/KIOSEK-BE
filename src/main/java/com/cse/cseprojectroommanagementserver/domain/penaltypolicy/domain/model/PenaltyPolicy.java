package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus.*;

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

    public static PenaltyPolicy createPenaltyPolicy(Integer violationCountToImposePenalty, Integer numberOfSuspensionDay) {
        return PenaltyPolicy.builder()
                .violationCountToImposePenalty(new ViolationCountToImposePenalty(violationCountToImposePenalty))
                .numberOfSuspensionDay(new NumberOfSuspensionDay(numberOfSuspensionDay))
                .appliedStatus(CURRENTLY)
                .build();
    }

    public boolean isPenaltyTarget(int countOfViolations) {
        return this.violationCountToImposePenalty.isPenaltyTarget(countOfViolations);
    }

    public void toDeprecated() {
        this.appliedStatus = DEPRECATED;
    }

}
