package com.cse.cseprojectroommanagementserver.domain.violation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<Violation, Long> {
}
