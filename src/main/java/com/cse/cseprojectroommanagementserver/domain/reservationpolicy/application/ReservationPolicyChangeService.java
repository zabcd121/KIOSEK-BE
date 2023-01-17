package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicyRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.NotExistsReservationPolicyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyRequestDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationPolicyChangeService {
    private final ReservationPolicyRepository reservationPolicyRepository;

    @Transactional
    public void changeReservationPolicy(ReservationPolicyChangeRequest changeRequest) {
        System.out.println("id는 " + changeRequest.getReservationPolicyId());
        ReservationPolicy originPolicy = reservationPolicyRepository.findById(changeRequest.getReservationPolicyId())
                .orElseThrow(() -> new NotExistsReservationPolicyException());

        reservationPolicyRepository.save(createNewReservationPolicy(changeRequest));
        originPolicy.toDeprecated();
    }

    private ReservationPolicy createNewReservationPolicy(ReservationPolicyChangeRequest changeRequest) {
        return ReservationPolicy.createReservationPolicy(changeRequest.getReservationMaxHourPerOnce(), changeRequest.getReservationMaxCountPerDay(), changeRequest.getReservationMaxPeriod());
    }
}
