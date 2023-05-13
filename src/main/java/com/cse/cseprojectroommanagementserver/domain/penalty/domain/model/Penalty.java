package com.cse.cseprojectroommanagementserver.domain.penalty.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.exception.AlreadyStoppedAccountUntilRequestDateException;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<Violation> violationList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private LocalDate startDt;

    @Column(nullable = false)
    private LocalDate endDt;

    public void extendEndDate(LocalDate newEndDt) {
        if(newEndDt.isBefore(this.endDt)) {
            throw new AlreadyStoppedAccountUntilRequestDateException();
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
                .endDt(LocalDate.now().plusDays(penaltyPolicy.getNumberOfSuspensionDay().getNumberOfSuspensionDay()-1))
                .build();

        return penalty;
    }

}
