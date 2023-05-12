package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto;

import lombok.*;

import javax.validation.constraints.NotNull;


public class PenaltyPolicyReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class PenaltyPolicyChangeReq {

        @NotNull
        private Long penaltyPolicyId;

        @NotNull
        private Integer violationCountToImposePenalty;

        @NotNull
        private Integer numberOfSuspensionDay;
    }



}
