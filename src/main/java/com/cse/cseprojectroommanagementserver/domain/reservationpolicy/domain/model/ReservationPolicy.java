package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model;

import com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReservationPolicy extends BaseTimeEntity {

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

    public static ReservationPolicy createReservationPolicy(Integer reservationMaxHourPerOnce, Integer reservationMaxCountPerDay, Integer reservationMaxPeriod) {
        return ReservationPolicy.builder()
                .reservationMaxHourPerOnce(new ReservationMaxHourPerOnce(reservationMaxHourPerOnce))
                .reservationMaxCountPerDay (new ReservationMaxCountPerDay(reservationMaxCountPerDay))
                .reservationMaxPeriod(new ReservationMaxPeriod(reservationMaxPeriod))
                .appliedStatus(CURRENTLY)
                .build();
    }

    public boolean verifyReservation(LocalDateTime reservationStartAt, LocalDateTime reservationEndAt, Long countTodayMemberCreatedReservation) {
        reservationMaxHourPerOnce.checkPolicy(reservationStartAt, reservationEndAt);
        reservationMaxCountPerDay.checkPolicy(countTodayMemberCreatedReservation);
        reservationMaxPeriod.checkPolicy(reservationEndAt);

        return true;
    }

    public void toDeprecated() {
        this.appliedStatus = DEPRECATED;
    }

}
