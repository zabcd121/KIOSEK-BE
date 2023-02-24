package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.InvalidReservationQRException;
import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationAuthService {

    private final ReservationSearchRepository reservationSearchableRepository;
    private final ReservationVerifiableRepository reservationVerifiableRepository;

    @Transactional
    public void checkInWIthReservationQR(QRAuthReq qrAuthReq) {
        Reservation findReservation = reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())
                .orElseThrow(() -> new InvalidReservationQRException());
        boolean isPreviousReservationInUse = reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt());
        findReservation.checkIn(isPreviousReservationInUse);
    }
}
