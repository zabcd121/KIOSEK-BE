package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import lombok.*;

public class ReservationPolicyResDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ReservationPolicySearchRes {
        private Long reservationPolicyId;
        private Integer maxHourPerOnce;
        private Integer maxCountPerDay;
        private Integer maxPeriod;

        public ReservationPolicySearchRes of(ReservationPolicy reservationPolicy) {
            this.reservationPolicyId = reservationPolicy.getReservationPolicyId();
            this.maxHourPerOnce = reservationPolicy.getReservationMaxHourPerOnce().getMaxHour();
            this.maxCountPerDay = reservationPolicy.getReservationMaxCountPerDay().getMaxCount();
            this.maxPeriod = reservationPolicy.getReservationMaxPeriod().getMaxPeriod();

            return this;
        }
    }
}
