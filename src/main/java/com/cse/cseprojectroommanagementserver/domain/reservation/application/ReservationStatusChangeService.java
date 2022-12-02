package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationStatusChangeService {
    private final ReservationSearchRepository reservationSearchRepository;

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation findReservation = reservationSearchRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotExistsReservationException());
        findReservation.cancel();
    }
}
