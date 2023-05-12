package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicyRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.NotFoundReservationPolicyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationPolicyChangeService {
    private final ReservationPolicyRepository reservationPolicyRepository;

    @Transactional
    public void changeReservationPolicy(ReservationPolicyChangeReq changeReq) {
        ReservationPolicy originPolicy = reservationPolicyRepository.findById(changeReq.getReservationPolicyId())
                .orElseThrow(() -> new NotFoundReservationPolicyException());

        reservationPolicyRepository.save(createNewReservationPolicy(changeReq));
        originPolicy.toDeprecated();
    }

    private ReservationPolicy createNewReservationPolicy(ReservationPolicyChangeReq changeRequest) {
        return ReservationPolicy.createReservationPolicy(changeRequest.getReservationMaxHourPerOnce(), changeRequest.getReservationMaxCountPerDay(), changeRequest.getReservationMaxPeriod());
    }
}
