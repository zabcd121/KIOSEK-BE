package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyPolicyRepository extends JpaRepository<PenaltyPolicy, Long> {
}
