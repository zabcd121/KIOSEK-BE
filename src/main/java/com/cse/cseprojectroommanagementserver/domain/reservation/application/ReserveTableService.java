package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.repository.PenaltySearchRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableSearchRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.DuplicatedReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.PenaltyMemberReserveFailException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicyRepository;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.util.AccountQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.QRNotCreatedException;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationRequestDto.ReserveRequest;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.ACCOUNT_QR_CREATE_FAIL;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveTableService {

    private final ReservationVerifiableRepository reservationVerifiableRepository;
    private final ReservationRepository reservationRepository;
    private final PenaltySearchRepository penaltySearchRepository;
    private final ReservationPolicyRepository reservationPolicyRepository;
    private final MemberSearchRepository memberSearchRepository;
    private final ProjectTableSearchRepository projectTableSearchRepository;
    private final QRGenerator qrGenerator;


    @Transactional
    public void reserve(ReserveRequest reserveRequest) {
        validateReservation(reserveRequest);
        Image reservationQrImage = null;

        try {
            reservationQrImage = qrGenerator.createReservationQRCodeImage();
        } catch (WriterException | IOException | QRNotCreatedException e) {
            throw new AccountQRNotCreatedException(ACCOUNT_QR_CREATE_FAIL);
        }

        reservationRepository.save(
                Reservation.createReservation(
                        memberSearchRepository.getReferenceById(reserveRequest.getMemberId()), //성능 향상을 위해 proxy 객체를 넣음
                        projectTableSearchRepository.getReferenceById(reserveRequest.getProjectTableId()),
                        reservationQrImage,
                        reserveRequest.getStartDateTime(),
                        reserveRequest.getEndDateTime()
                ));

    }

    @Transactional
    public void reserveOnsite(ReserveRequest reserveRequest) {
        validateReservation(reserveRequest);

        reservationRepository.save(
                Reservation.createOnSiteReservation(
                        memberSearchRepository.getReferenceById(reserveRequest.getMemberId()), //성능 향상을 위해 proxy 객체를 넣음
                        projectTableSearchRepository.getReferenceById(reserveRequest.getProjectTableId()),
                        reserveRequest.getStartDateTime(),
                        reserveRequest.getEndDateTime()
                ));
    }

    private void validateReservation(ReserveRequest reserveRequest) {
        isPenaltyMember(reserveRequest.getMemberId());
        isDuplicatedReservation(reserveRequest.getProjectTableId(), reserveRequest.getStartDateTime(), reserveRequest.getEndDateTime());

        ReservationPolicy reservationPolicy = findReservationPolicy();
        //오늘 이 회원이 예약을 실행한 횟수룰 가져옴
        Long countTodayMemberCreatedReservation = getCountTodayMemberCreatedReservation(reserveRequest.getMemberId());
        reservationPolicy.verifyReservation(reserveRequest.getStartDateTime(), reserveRequest.getEndDateTime(), countTodayMemberCreatedReservation);
    }

    private boolean isPenaltyMember(Long memberId) {
        if (penaltySearchRepository.existsByMemberId(memberId)) {
            throw new PenaltyMemberReserveFailException();
        }
        return false;
    }

    private boolean isDuplicatedReservation(Long tableId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (reservationVerifiableRepository.existsBy(tableId, startDateTime, endDateTime)) {
            throw new DuplicatedReservationException();
        }
        log.info("중복 예약 아님");
        return false;
    }

    private ReservationPolicy findReservationPolicy() {
        return reservationPolicyRepository.findByAppliedStatus(AppliedStatus.CURRENT);
    }

    private Long getCountTodayMemberCreatedReservation(Long memberId) {
        return reservationVerifiableRepository.countCreatedReservationForTodayByMemberId(memberId);
    }

}