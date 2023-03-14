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

    // 체크인은 내가 예약한 테이블이 이전 예약자가 사용을 하고 있지 않을 경우 시작시간 10분전부터 체크인 가능하다.
    @Transactional
    public void checkInWIthReservationQR(QRAuthReq qrAuthReq) {
        Reservation findReservation = reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())
                .orElseThrow(() -> new InvalidReservationQRException());
        boolean isPreviousReservationInUse = reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt());
        findReservation.checkIn(isPreviousReservationInUse);
    }
}
