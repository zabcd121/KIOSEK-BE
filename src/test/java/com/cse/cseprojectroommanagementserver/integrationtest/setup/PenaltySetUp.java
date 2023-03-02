package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PenaltySetUp {

    @Autowired
    private PenaltyRepository penaltyRepository;

    public Penalty savePenalty(Member member, LocalDate startDt, LocalDate endDt) {
        return penaltyRepository.save(
                Penalty.builder()
                        .member(member)
                        .description("청소 불량")
                        .startDt(startDt)
                        .endDt(endDt)
                        .build()
        );
    }
}
