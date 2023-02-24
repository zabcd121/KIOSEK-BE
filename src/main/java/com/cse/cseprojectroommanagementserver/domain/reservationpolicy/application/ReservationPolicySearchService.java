package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationPolicySearchService {
    private ReservationPolicySearchableRepository reservationPolicySearchableRepository;

    @Transactional
    public ReservationPolicySearchRes searchReservationPolicy() {
        return new ReservationPolicySearchRes()
                .of(reservationPolicySearchableRepository.findCurrentlyPolicy());
    }
}
