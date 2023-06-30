package com.cse.cseprojectroommanagementserver.domain.violation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ProcessingStatus.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Violation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long violationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member targetMember;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true, nullable = false)
    private Reservation targetReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @Column(nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    private ViolationContent violationContent;

    @Column(nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private ProcessingStatus processingStatus;

    public static Violation createViolation(Reservation reservation, ViolationContent violationContent) {
        return Violation.builder()
                .targetMember(reservation.getMember())
                .violationContent(violationContent)
                .processingStatus(NON_PENALIZED)
                .targetReservation(reservation)
                .build();
    }

    public void changePenalty(Penalty penalty) {
        if(this.penalty != null) {
            this.penalty.getViolationList().remove(this);
        }
        this.penalty = penalty;
        this.penalty.getViolationList().add(this);
        this.processingStatus = PENALIZED;
    }

}
