package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicyRepository;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltyPolicyChangeService {

    private final PenaltyPolicyRepository penaltyPolicyRepository;

    @Transactional
    public void changePenaltyPolicy(PenaltyPolicyChangeReq changeReq) {
        PenaltyPolicy originPolicy = penaltyPolicyRepository.findById(changeReq.getPenaltyPolicyId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PENALTY_POLICY));

        penaltyPolicyRepository.save(createNewPenaltyPolicy(changeReq));
        originPolicy.toDeprecated();

    }

    private PenaltyPolicy createNewPenaltyPolicy(PenaltyPolicyChangeReq changeReq) {
        return PenaltyPolicy.createPenaltyPolicy(changeReq.getViolationCountToImposePenalty(), changeReq.getNumberOfSuspensionDay());
    }
}
