package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicyRepository;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.exception.NotExistsPenaltyPolicyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyRequestDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltyPolicyChangeService {

    private final PenaltyPolicyRepository penaltyPolicyRepository;

    @Transactional
    public void changePenaltyPolicy(PenaltyPolicyChangeRequest changeReq) {
        PenaltyPolicy originPolicy = penaltyPolicyRepository.findById(changeReq.getPenaltyPolicyId())
                .orElseThrow(() -> new NotExistsPenaltyPolicyException());

        penaltyPolicyRepository.save(createNewPenaltyPolicy(changeReq));
        originPolicy.toDeprecated();

    }

    private PenaltyPolicy createNewPenaltyPolicy(PenaltyPolicyChangeRequest changeReq) {
        return PenaltyPolicy.createPenaltyPolicy(changeReq.getViolationCountToImposePenalty(), changeReq.getNumberOfSuspensionDay());
    }
}
