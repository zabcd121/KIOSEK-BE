package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto;

import lombok.*;


public class PenaltyPolicyReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PenaltyPolicyChangeReq {
        private Long penaltyPolicyId;
        private Integer violationCountToImposePenalty;
        private Integer numberOfSuspensionDay;
    }



}
