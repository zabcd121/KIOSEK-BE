package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import lombok.*;

public class PenaltyPolicyResDto {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class PenaltyPolicySearchRes {
        private Long penaltyPolicyId;
        private Integer violationCountToImposePenalty;
        private Integer numberOfSuspensionDay;

        public static PenaltyPolicySearchRes of(PenaltyPolicy penaltyPolicy) {
            return PenaltyPolicySearchRes.builder()
                    .penaltyPolicyId(penaltyPolicy.getPenaltyPolicyId())
                    .violationCountToImposePenalty(penaltyPolicy.getViolationCountToImposePenalty().getCountOfViolationsToImposePenalty())
                    .numberOfSuspensionDay(penaltyPolicy.getNumberOfSuspensionDay().getNumberOfSuspensionDay())
                    .build();
        }
    }

}
