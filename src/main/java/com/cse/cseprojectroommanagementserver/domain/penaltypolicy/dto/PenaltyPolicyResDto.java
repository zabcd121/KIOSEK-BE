package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import lombok.*;

public class PenaltyPolicyResDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PenaltyPolicySearchRes {
        private Long penaltyPolicyId;
        private Integer violationCountToImposePenalty;
        private Integer numberOfSuspensionDay;

        public PenaltyPolicySearchRes of(PenaltyPolicy penaltyPolicy) {
            this.penaltyPolicyId = penaltyPolicy.getPenaltyPolicyId();
            this.violationCountToImposePenalty = penaltyPolicy.getViolationCountToImposePenalty().getCountOfViolationsToImposePenalty();
            this.numberOfSuspensionDay = penaltyPolicy.getNumberOfSuspensionDay().getNumberOfSuspensionDay();

            return this;
        }
    }

}
