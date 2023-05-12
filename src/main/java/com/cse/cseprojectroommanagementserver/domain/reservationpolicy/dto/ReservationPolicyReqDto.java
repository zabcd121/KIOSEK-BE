package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

public class ReservationPolicyReqDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationPolicyChangeReq {

        @NotNull
        private Long reservationPolicyId;

        @NotNull
        private Integer reservationMaxHourPerOnce;

        @NotNull
        private Integer reservationMaxCountPerDay;

        @NotNull
        private Integer reservationMaxPeriod;
    }

}
