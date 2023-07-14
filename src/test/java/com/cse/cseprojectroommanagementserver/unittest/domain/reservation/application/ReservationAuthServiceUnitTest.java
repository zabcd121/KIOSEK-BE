package com.cse.cseprojectroommanagementserver.unittest.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationAuthService;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.repository.ReservationSearchRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationqr.domain.model.ReservationQR;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessRuleException;
import com.cse.cseprojectroommanagementserver.global.error.exception.IncorrectException;
import com.cse.cseprojectroommanagementserver.global.error.exception.PolicyInfractionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.ReservationFixedPolicy.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationAuthServiceUnitTest {

    @InjectMocks
    ReservationAuthService reservationAuthService;

    @Mock
    ReservationSearchRepository reservationSearchableRepository;
    @Mock
    ReservationVerifiableRepository reservationVerifiableRepository;

    QRAuthReq qrAuthReq;

    @BeforeEach
    void setUp() {
        qrAuthReq = new QRAuthReq("example");
    }

    /**
     * 테스트 케이스
     * C1. 예약 QR 인증 성공
     * C2. 예약 QR 인증 실패
         * C2-01. 예약 QR 인증 실패 - qr content 불일치
         * C2-02. 예약 QR 인증 실패 - 아직 이전 사용자가 테이블을 사용중인 경우 (시작 시간 10분전이지만 이전 사용자가 사용중이면 체크인 불가능)
         * C2-03. 예약 QR 인증 실패 - 체크인 가능한 시간이 아닌 경우1 (이전 사용자가 사용중이 아니고 시작시간 10분보다 더 남은 경우 체크인 불가능)
         * C2-04. 예약 QR 인증 실패 - 체크인 가능한 시간이 아닌 경우2 (시작시간 20분 후에는 체크인이 불가능함)
     */
    @Test
    @DisplayName("C1. 예약 QR 인증 성공 - 예약 상태가 사용중으로 바뀜")
    void 예약QR인증_성공() {
        // Given
        Reservation findReservation = Reservation.builder()
                .reservationId(1L)
                .startAt(LocalDateTime.now().plusMinutes(POSSIBLE_CHECKIN_TIME_BEFORE.getValue()))
                .endAt(LocalDateTime.now().plusHours(1))
                .reservationQR(
                        ReservationQR.builder()
                                .reservationQRId(1L)
                                .qrImage(QRImage.builder().content("example").build())
                                .build())
                .reservationStatus(RESERVATION_COMPLETED)
                .projectTable(
                        ProjectTable.builder()
                                .tableId(1L)
                                .build()
                )
                .build();
        given(reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())).willReturn(Optional.of(findReservation));
        given(reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt())).willReturn(false);

        // When
        reservationAuthService.checkInWIthReservationQR(qrAuthReq);

        // Then
        assertEquals(IN_USE, findReservation.getReservationStatus());
    }

    @Test
    @DisplayName("C2-01. 예약 QR 인증 실패 - qr content 불일치")
    void 예약QR인증_실패_QR내용불일치() {
        //Given
        given(reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())).willReturn(Optional.ofNullable(null));

        // When, Then
        assertThrows(IncorrectException.class, () -> reservationAuthService.checkInWIthReservationQR(qrAuthReq));
    }

    @Test
    @DisplayName("C2-02. 예약 QR 인증 실패 - 아직 이전 사용자가 테이블을 사용중인 경우 (시작 시간 10분전이지만 이전 사용자가 사용중이면 체크인 불가능)")
    void 예약QR인증_실패_이전사용자_아직사용중() {
        //Given
        Reservation findReservation = Reservation.builder()
                .reservationId(1L)
                .startAt(LocalDateTime.now().plusMinutes(POSSIBLE_CHECKIN_TIME_BEFORE.getValue()))
                .endAt(LocalDateTime.now().plusHours(1))
                .reservationQR(
                        ReservationQR.builder()
                                .reservationQRId(1L)
                                .qrImage(QRImage.builder().content("example").build())
                                .build())
                .reservationStatus(RESERVATION_COMPLETED)
                .projectTable(
                        ProjectTable.builder()
                                .tableId(1L)
                                .build()
                )
                .build();
        given(reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())).willReturn(Optional.of(findReservation));
        given(reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt())).willReturn(true);

        // When, Then
        assertThrows(BusinessRuleException.class, () -> reservationAuthService.checkInWIthReservationQR(qrAuthReq));
    }

    @Test
    @DisplayName("C2-03. 예약 QR 인증 실패 - 체크인 가능한 시간이 아닌 경우1 (이전 사용자가 사용중이 아니고 시작시간 10분보다 더 남은 경우 체크인 불가능)")
    void 예약QR인증_실패_체크인불가능한시간1() {
        //Given
        Reservation findReservation = Reservation.builder()
                .reservationId(1L)
                .startAt(LocalDateTime.now().plusMinutes(POSSIBLE_CHECKIN_TIME_BEFORE.getValue()).plusSeconds(1))
                .endAt(LocalDateTime.now().plusHours(1))
                .reservationQR(
                        ReservationQR.builder()
                                .reservationQRId(1L)
                                .qrImage(QRImage.builder().content("example").build())
                                .build())
                .reservationStatus(RESERVATION_COMPLETED)
                .projectTable(
                        ProjectTable.builder()
                                .tableId(1L)
                                .build()
                )
                .build();
        given(reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())).willReturn(Optional.of(findReservation));
        given(reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt())).willReturn(false);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationAuthService.checkInWIthReservationQR(qrAuthReq));
    }

    @Test
    @DisplayName("C2-04. 예약 QR 인증 실패 - 체크인 가능한 시간이 아닌 경우2 (시작시간 20분 후에는 체크인이 불가능함)")
    void 예약QR인증_실패_체크인불가능한시간2() {
        //Given
        Reservation findReservation = Reservation.builder()
                .reservationId(1L)
                .startAt(LocalDateTime.now().minusMinutes(POSSIBLE_CHECKIN_TIME_AFTER.getValue()).minusSeconds(1))
                .endAt(LocalDateTime.now().plusHours(1))
                .reservationQR(
                        ReservationQR.builder()
                                .reservationQRId(1L)
                                .qrImage(QRImage.builder().content("example").build())
                                .build())
                .reservationStatus(RESERVATION_COMPLETED)
                .projectTable(
                        ProjectTable.builder()
                                .tableId(1L)
                                .build()
                )
                .build();
        given(reservationSearchableRepository.findByQRContents(qrAuthReq.getQrContent())).willReturn(Optional.of(findReservation));
        given(reservationVerifiableRepository.existsCurrentlyInUseTableBy(findReservation.getProjectTable().getTableId(), findReservation.getStartAt())).willReturn(false);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationAuthService.checkInWIthReservationQR(qrAuthReq));
    }

}