package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import lombok.*;

public class ReservationPolicyResDto {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ReservationPolicySearchRes {
        private Long reservationPolicyId;
        private Integer maxHourPerOnce;
        private Integer maxCountPerDay;
        private Integer maxPeriod;

        public static ReservationPolicySearchRes of(ReservationPolicy reservationPolicy) {
            return ReservationPolicySearchRes.builder()
                    .reservationPolicyId(reservationPolicy.getReservationPolicyId())
                    .maxHourPerOnce(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour())
                    .maxCountPerDay(reservationPolicy.getReservationMaxCountPerDay().getMaxCount())
                    .maxPeriod(reservationPolicy.getReservationMaxPeriod().getMaxPeriod())
                    .build();
        }
    }
}
