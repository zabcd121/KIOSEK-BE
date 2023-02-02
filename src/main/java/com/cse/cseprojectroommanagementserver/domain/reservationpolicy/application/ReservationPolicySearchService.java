package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyResponseDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationPolicySearchService {
    private ReservationPolicySearchableRepository reservationPolicySearchableRepository;

    @Transactional
    public ReservationPolicySearchResponse searchReservationPolicy() {
        return new ReservationPolicySearchResponse()
                .of(reservationPolicySearchableRepository.findCurrentlyPolicy());
    }
}
