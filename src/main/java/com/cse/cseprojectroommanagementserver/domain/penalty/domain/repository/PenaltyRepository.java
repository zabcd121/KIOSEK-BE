package com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
