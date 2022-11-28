package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationPolicyRepository extends JpaRepository<ReservationPolicy, Long> {
    ReservationPolicy findByAppliedStatus(AppliedStatus appliedStatus);
}
