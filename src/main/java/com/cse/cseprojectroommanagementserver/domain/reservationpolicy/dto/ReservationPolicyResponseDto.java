package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import lombok.*;

public class ReservationPolicyResponseDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ReservationPolicySearchResponse {
        private Long reservationPolicyId;
        private Integer reservationMaxHourPerOnce;
        private Integer reservationMaxCountPerDay;
        private Integer reservationMaxPeriod;

        public ReservationPolicySearchResponse of(ReservationPolicy reservationPolicy) {
            this.reservationPolicyId = reservationPolicy.getReservationPolicyId();
            this.reservationMaxHourPerOnce = reservationPolicy.getReservationMaxHourPerOnce().getMaxHourPerOnce();
            this.reservationMaxCountPerDay = reservationPolicy.getReservationMaxCountPerDay().getMaxCountPerDay();
            this.reservationMaxPeriod = reservationPolicy.getReservationMaxPeriod().getMaxPeriod();

            return this;
        }
    }

}
