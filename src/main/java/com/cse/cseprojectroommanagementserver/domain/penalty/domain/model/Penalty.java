package com.cse.cseprojectroommanagementserver.domain.penalty.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Penalty extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penaltyId;

    @OneToMany(mappedBy = "penalty", fetch = FetchType.LAZY)
    private List<Violation> violations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public void addViolation(Violation violation) {
        violation.changePenalty(this);
        this.violations.add(violation);
    }

    public static Penalty createPenalty(Member member, PenaltyPolicy penaltyPolicy, List<Violation> violations) {
        Penalty penalty = Penalty.builder()
                .member(member)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(penaltyPolicy.getNumberOfSuspensionDay()))
                .build();

        for (Violation violation : violations) {
            penalty.addViolation(violation);
        }

        return penalty;
    }
}
