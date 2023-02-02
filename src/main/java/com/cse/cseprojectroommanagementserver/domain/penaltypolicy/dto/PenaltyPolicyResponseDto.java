package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import lombok.*;

public class PenaltyPolicyResponseDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PenaltyPolicySearchResponse {
        private Long penaltyPolicyId;
        private Integer violationCountToImposePenalty;
        private Integer numberOfSuspensionDay;

        public PenaltyPolicySearchResponse of(PenaltyPolicy penaltyPolicy) {
            this.penaltyPolicyId = penaltyPolicy.getPenaltyPolicyId();
            this.violationCountToImposePenalty = penaltyPolicy.getViolationCountToImposePenalty().getCountOfViolationsToImposePenalty();
            this.numberOfSuspensionDay = penaltyPolicy.getNumberOfSuspensionDay().getNumberOfSuspensionDay();

            return this;
        }
    }

}
