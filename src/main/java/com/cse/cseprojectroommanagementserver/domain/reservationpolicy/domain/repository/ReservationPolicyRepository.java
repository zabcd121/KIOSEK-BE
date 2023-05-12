package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationPolicyRepository extends JpaRepository<ReservationPolicy, Long> {
    ReservationPolicy findByAppliedStatus(AppliedStatus appliedStatus);
}
