package com.cse.cseprojectroommanagementserver.domain.penalty.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.exception.ImpossibleExtensionReqException;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ProcessingStatus.PENALIZED;
import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ViolationContent.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Penalty extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long penaltyId;

    @Builder.Default
    @OneToMany(mappedBy = "penalty", fetch = FetchType.LAZY)
    private List<Violation> violations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String description;
    private LocalDate startDt;
    private LocalDate endDt;

    public void extendEndDate(LocalDate newEndDt) {
        if(newEndDt.isBefore(this.endDt)) {
            throw new ImpossibleExtensionReqException();
        }
        this.endDt = newEndDt;
    }

    public static Penalty createPenalty(Member member, PenaltyPolicy penaltyPolicy, List<Violation> violationList) {
        int unusedCnt = 0;
        int notReturnedCnt = 0;
        for (Violation violation : violationList) {
            if(violation.getViolationContent().equals(UN_USED_CONTENT) ) unusedCnt++;
            else if(violation.getViolationContent().equals(NOT_RETURNED_CONTENT) ) notReturnedCnt++;
        }

        String description = UN_USED_CONTENT.getContent() + " " + unusedCnt + "회 " + NOT_RETURNED_CONTENT.getContent() + " " + notReturnedCnt + "회";

        Penalty penalty = Penalty.builder()
                .member(member)
                .description(description)
                .startDt(LocalDate.now())
                .endDt(LocalDate.now().plusDays(penaltyPolicy.getNumberOfSuspensionDay().getNumberOfSuspensionDay()))
                .build();

//        for (Violation violation : violationList) {
//            violation.changePenalty(penalty);
//        }

        return penalty;
    }

//    public void addViolation(Violation violation) {
//        this.violations.add(violation);
//
//        if(violation.getPenalty() != this) {
//            violation.changePenalty(this);
//        }
//    }


}
