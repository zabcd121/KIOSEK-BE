package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto;

import lombok.*;

public class ReservationPolicyReqDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ReservationPolicyChangeReq {
        private Long reservationPolicyId;
        private Integer reservationMaxHourPerOnce;
        private Integer reservationMaxCountPerDay;
        private Integer reservationMaxPeriod;
    }

}
