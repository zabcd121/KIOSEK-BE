package com.cse.cseprojectroommanagementserver.domain.violation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import lombok.*;

import javax.persistence.*;

import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ProcessingStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ViolationContent.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Violation {

    @Id @GeneratedValue
    private Long violationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member targetMember;

    @Enumerated(value = EnumType.STRING)
    private ViolationContent violationContent;

    @Enumerated(value = EnumType.STRING)
    private ProcessingStatus processingStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation targetReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    public static Violation createNotReturnedViolation(Reservation reservation) {
        return Violation.builder()
                .targetMember(reservation.getMember())
                .violationContent(NOT_RETURNED)
                .processingStatus(NON_PENALIZED)
                .targetReservation(reservation)
                .build();
    }

    public void changePenalty(Penalty penalty) {
        this.penalty = penalty;
        this.processingStatus = PENALIZED;
    }

}
