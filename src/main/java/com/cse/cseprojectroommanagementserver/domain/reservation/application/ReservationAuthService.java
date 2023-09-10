package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.IncorrectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationAuthService {

    private final ReservationSearchRepository reservationSearchableRepository;
    private final ReservationVerifiableRepository reservationVerifiableRepository;

    // 체크인은 내가 예약한 테이블이 이전 예약자가 사용을 하고 있지 않을 경우 시작시간 20분전부터 체크인 가능하다.
    public void checkInWithReservationQR(QRAuthReq qrAuthReq) {
        Reservation findReservation = reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())
                .orElseThrow(() -> new IncorrectException(ErrorCode.INCORRECT_AUTH_CODE));

        // 이전 사용자가 아직 테이블을 사용중이라면 미리 체크인 불가능
        boolean isPreviousMemberInUse = reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt());

        findReservation.checkIn(isPreviousMemberInUse);
    }
}
