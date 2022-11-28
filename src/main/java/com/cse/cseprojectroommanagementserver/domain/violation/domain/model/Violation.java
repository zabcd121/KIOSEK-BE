package com.cse.cseprojectroommanagementserver.domain.violation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Violation {

    @Id @GeneratedValue
    private Long violationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member targetMember;

    @Enumerated(value = EnumType.STRING)
    private ViolationContent violationContent;

    @Enumerated(value = EnumType.STRING)
    private ProcessingStatus penaltyStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation targetReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

}
