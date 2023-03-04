package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicyRepository;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationPolicySetUp {

    @Autowired
    private ReservationPolicyRepository reservationPolicyRepository;

    public ReservationPolicy saveReservationPolicy(Integer maxHour, Integer maxCount, Integer maxPeriod) {
        return reservationPolicyRepository.saveAndFlush(
                ReservationPolicy.createReservationPolicy(maxHour, maxCount, maxPeriod)
        );
    }

    public ReservationPolicy findReservationPolicy() {
        return reservationPolicyRepository.findByAppliedStatus(AppliedStatus.CURRENTLY);
    }
}
