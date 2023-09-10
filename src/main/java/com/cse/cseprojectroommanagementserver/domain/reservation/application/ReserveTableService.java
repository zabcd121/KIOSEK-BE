package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.validator.ReservationValidator;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.qrgenerator.QRGenerator;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.ReserveReq;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReserveTableService {

    private final ReservationValidator reservationValidator;
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final ProjectTableRepository projectTableRepository;
    private final QRGenerator qrGenerator;

    @Transactional
    public Reservation reserve(Long memberId, ReserveReq reserveReq) {
        reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt());

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
     * 현장예약: FORM 로그인과 동시에 예약까지 한번에 진행하는 메서드 (현재 사용하지 않음)
     * @param
     */
//    @Transactional
//    public void reserveOnsiteByFormLogin(Member member, OnsiteReservationByLoginFormReq reservationRequest) {
//        reserveOnsite(member, reservationRequest.getProjectTableId(), reservationRequest.getStartAt(), reservationRequest.getEndAt() );
//    }

    private void reserveOnsite(Member member, Long projectTableId, LocalDateTime startAt, LocalDateTime endAt) {
        reservationValidator.validate(member.getMemberId(), projectTableId, startAt, endAt);

        reservationRepository.save(
                Reservation.createOnSiteReservation(
                        memberRepository.getReferenceById(member.getMemberId()),
                        projectTableRepository.getReferenceById(projectTableId),
                        startAt,
                        endAt
                ));
    }



}