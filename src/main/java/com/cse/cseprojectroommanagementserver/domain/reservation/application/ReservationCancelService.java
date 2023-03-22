package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.NotExistsReservationException;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationCancelService {
    private final ReservationSearchableRepository reservationSearchableRepository;

    @Timed("kiosek.reservation")
    @Transactional
    public void cancelReservation(Long memberId, Long reservationId) {
        Reservation findReservation = reservationSearchableRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotExistsReservationException());

        findReservation.cancel(memberId); //내 예약만 취소할 수 있음.
    }
}
