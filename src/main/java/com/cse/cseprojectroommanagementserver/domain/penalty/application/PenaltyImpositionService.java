package com.cse.cseprojectroommanagementserver.domain.penalty.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltyRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltyImpositionService {

    private final PenaltyRepository penaltyRepository;
    private final PenaltySearchableRepository penaltySearchableRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void imposePenalty(PenaltyImpositionReq penaltyReq) {
        Optional<Penalty> inProgressPenalty = penaltySearchableRepository.findInProgressByMemberId(penaltyReq.getMemberId());

        if(inProgressPenalty.isPresent()) {
            inProgressPenalty.get().extendEndDate(penaltyReq.getEndDt());
        } else {
            penaltyRepository.save(penaltyReq.toEntity(memberRepository.getReferenceById(penaltyReq.getMemberId())));
        }
    }
}
