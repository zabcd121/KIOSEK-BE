package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicySearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltyPolicySearchService {

    private final PenaltyPolicySearchableRepository penaltyPolicySearchableRepository;

    @Transactional
    public PenaltyPolicySearchRes searchPenaltyPolicy() {
        return PenaltyPolicySearchRes.of(penaltyPolicySearchableRepository.findCurrentPenaltyPolicy());
    }
}
