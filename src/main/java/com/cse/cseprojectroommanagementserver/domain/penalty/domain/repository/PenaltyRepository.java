package com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
}
