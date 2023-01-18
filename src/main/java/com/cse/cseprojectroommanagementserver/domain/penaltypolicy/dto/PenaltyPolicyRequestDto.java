package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto;

import lombok.*;


public class PenaltyPolicyRequestDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PenaltyPolicyChangeRequest {
        private Long penaltyPolicyId;
        private Integer violationCountToImposePenalty;
        private Integer numberOfSuspensionDay;
    }
}
