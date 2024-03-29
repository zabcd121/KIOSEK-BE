package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus.*;

@Component
public class PenaltyPolicySetUp {

    @Autowired
    private PenaltyPolicyRepository penaltyPolicyRepository;

    public PenaltyPolicy savePenaltyPolicy(Integer violationCountToImposePenalty, Integer numberOfSuspensionDay) {
        return penaltyPolicyRepository.save(
                PenaltyPolicy.createPenaltyPolicy(violationCountToImposePenalty, numberOfSuspensionDay)
        );
    }

    public PenaltyPolicy findPenaltyPolicy() {
        return penaltyPolicyRepository.findByAppliedStatus(CURRENTLY);
    }
}
