package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.*;
import com.cse.cseprojectroommanagementserver.global.util.qrgenerator.QRGenerator;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.ReserveReq;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveTableService {

    private final ReservationVerifiableRepository reservationVerifiableRepository;
    private final ReservationRepository reservationRepository;
    private final PenaltySearchableRepository penaltySearchRepository;
    private final ReservationPolicySearchableRepository reservationPolicySearchableRepository;
    private final MemberRepository memberRepository;
    private final ProjectTableRepository projectTableRepository;
    private final TableDeactivationSearchableRepository tableDeactivationSearchableRepository;
    private final QRGenerator qrGenerator;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Reservation reserve(Long memberId, ReserveReq reserveReq) {
        validateReservation(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt());

        QRImage reservationQrImage = qrGenerator.createReservationQRCodeImage();

        return reservationRepository.save(
                Reservation.createReservation(
                        memberRepository.getReferenceById(memberId), //성능 향상을 위해 proxy 객체를 넣음
                        projectTableRepository.getReferenceById(reserveReq.getProjectTableId()),
                        reservationQrImage,
                        reserveReq.getStartAt(),
                        reserveReq.getEndAt()
                ));
    }

    /**
     * 현장예약: QR 로그인과 동시에 예약까지 한번에 진행함
     * @param
     */
    @Timed("kiosek.reservation")
    @Transactional
    public void reserveOnsiteByAccountQR(Member member, OnsiteReservationByQRReq reservationRequest) {
        reserveOnsite(member, reservationRequest.getProjectTableId(), reservationRequest.getStartAt(), reservationRequest.getEndAt());
    }

    /**
     * 현장예약: FORM 로그인과 동시에 예약까지 한번에 진행함
     * @param
     */
//    @Transactional
//    public void reserveOnsiteByFormLogin(Member member, OnsiteReservationByLoginFormReq reservationRequest) {
//        reserveOnsite(member, reservationRequest.getProjectTableId(), reservationRequest.getStartAt(), reservationRequest.getEndAt() );
//    }

    private void reserveOnsite(Member member, Long projectTableId, LocalDateTime startAt, LocalDateTime endAt) {
        validateReservation(member.getMemberId(), projectTableId, startAt, endAt);

        reservationRepository.save(
                Reservation.createOnSiteReservation(
                        memberRepository.getReferenceById(member.getMemberId()),
                        projectTableRepository.getReferenceById(projectTableId),
                        startAt,
                        endAt
                ));
    }

    private void validateReservation(Long memberId, Long projectTableId, LocalDateTime startAt, LocalDateTime endAt) {
        if(!isPenaltyMember(memberId)
                && !isDisabledTable(projectTableId, startAt, endAt)
                && !isDuplicatedReservation(projectTableId, startAt, endAt)
                && !isEndAtAfterStartAt(startAt, endAt)) {
            ReservationPolicy reservationPolicy = findReservationPolicy();
            //오늘 이 회원이 예약을 실행한 횟수룰 가져옴
            Long countTodayMemberCreatedReservation = getCountTodayMemberCreatedReservation(memberId);
            reservationPolicy.verifyReservation(startAt, endAt, countTodayMemberCreatedReservation);
        }
    }


    private boolean isPenaltyMember(Long memberId) {
        if (penaltySearchRepository.existsByMemberId(memberId)) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_PENALTY_MEMBER);
        }
        return false;
    }

    private boolean isDisabledTable(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        if(tableDeactivationSearchableRepository.existsBy(tableId, startAt, endAt)) {
            throw new BusinessRuleException(ErrorCode.DISABLED_TABLE);
        }
        return false;
    }

    private boolean isDuplicatedReservation(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        if (reservationVerifiableRepository.existsBy(tableId, startAt, endAt)) {
            throw new DuplicationException(ErrorCode.DUPLICATED_RESERVATION);
        }
        return false;
    }

    private boolean isEndAtAfterStartAt(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new InvalidInputException(ErrorCode.INVALID_VALUE_ENDAT);
        }
        return false;
    }

    private ReservationPolicy findReservationPolicy() {
        return reservationPolicySearchableRepository.findCurrentlyPolicy();
    }

    private Long getCountTodayMemberCreatedReservation(Long memberId) {
        return reservationVerifiableRepository.countCreatedReservationForTodayBy(memberId);
    }

}