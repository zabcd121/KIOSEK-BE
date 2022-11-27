package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationPolicy {

    @Id @GeneratedValue
    private Long reservationPolicyId;

    @Embedded
    private ReservationMaxHourPerOnce reservationMaxHourPerOnce;

    @Embedded
    private ReservationMaxCountPerDay reservationMaxCountPerDay;

    @Embedded
    private ReservationMaxPeriod reservationMaxPeriod;


    @Enumerated(EnumType.STRING)
    private AppliedStatus appliedStatus;

    public boolean verifyReservation(LocalDateTime reservationStartDateTime, LocalDateTime reservationEndDateTime, Long countTodayMemberCreatedReservation) {
        reservationMaxHourPerOnce.checkPolicy(reservationStartDateTime, reservationEndDateTime);
        reservationMaxCountPerDay.checkPolicy(countTodayMemberCreatedReservation);
        reservationMaxPeriod.checkPolicy(reservationEndDateTime);

        return true;
    }

}
