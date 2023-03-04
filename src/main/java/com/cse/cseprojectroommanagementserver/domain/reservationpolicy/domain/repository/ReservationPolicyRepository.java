package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationPolicyRepository extends JpaRepository<ReservationPolicy, Long> {
    ReservationPolicy findByAppliedStatus(AppliedStatus appliedStatus);
}
