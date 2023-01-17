package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto;

import lombok.*;

public class ReservationPolicyRequestDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ReservationPolicyChangeRequest {
        private Long reservationPolicyId;
        private Integer reservationMaxHourPerOnce;
        private Integer reservationMaxCountPerDay;
        private Integer reservationMaxPeriod;
    }

}
