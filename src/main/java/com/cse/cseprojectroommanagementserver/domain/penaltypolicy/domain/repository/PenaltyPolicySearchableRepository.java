package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;

public interface PenaltyPolicySearchableRepository {

    PenaltyPolicy findCurrentPenaltyPolicy();
}
