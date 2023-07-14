package com.cse.cseprojectroommanagementserver.unittest.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReserveTableService;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.service.ReservationValidator;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.error.exception.*;
import com.cse.cseprojectroommanagementserver.global.util.qrgenerator.QRGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveTableServiceUnitTest {

    @InjectMocks
    ReserveTableService reserveTableService;

    @Mock ReservationValidator reservationValidator;

    @Mock ReservationRepository reservationRepository;
    @Mock MemberRepository memberRepository;
    @Mock ProjectTableRepository projectTableRepository;

    @Mock QRGenerator qrGenerator;

    ReservationPolicy reservationPolicy;
    static final int DAYS_OF_WEEK = 7;

    /** 테스트 케이스 ( Validator 는 분리하여 테스트 )
     * C1. 예약 성공 - 모두 통과
     * C2. 예약 실패
         * C2-01. 예약 실패 - 검증 실패 (검증확인은 Validator 단위 테스트에서)
     */

    @BeforeEach
    void setUp() {

        /** 예약 정책 중 최대 주기에 대한 설명
         * 최대 주기 값이 2라면 2주를 뜻하고 오늘을 제외한 2주 뒤까지 예약이 활성화 된다.
         * 날짜별로 예약이 당일 08시부터 익일 08시까지 가능하기 때문에
         * 최대주기가 1주이고 오늘이 1일이라면 8일까지 예약이 가능한건데 8일에는 9일 08시까지 예약이 가능하므로 실질적으로 1일부터 9일 08시까지 예약이 가능하다.
         */
        reservationPolicy = ReservationPolicy.createReservationPolicy(4, 1, 2);
    }

    @Test
    @DisplayName("C1. 예약 성공 - 모두 통과")
    void 예약_성공() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour());
        Long memberId = 3L;
        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();

        willDoNothing().given(reservationValidator).validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt());
        given(memberRepository.getReferenceById(memberId)).willReturn(Member.builder().memberId(memberId).build());
        given(projectTableRepository.getReferenceById(reserveReq.getProjectTableId())).willReturn(ProjectTable.builder().tableId(reserveReq.getProjectTableId()).build());
        Reservation savedReservation = Reservation.builder().build();
        given(reservationRepository.save(any())).willReturn(savedReservation);

        // When
        reserveTableService.reserve(memberId, reserveReq);

        // Then
        assertDoesNotThrow(() -> FileSystemException.class);
        then(reservationRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("C2-01. 예약 실패 - 검증실패")
    void 예약_실패_검증실패() {
        // Given
        Long memberId = 3L;
        ReserveReq reserveReq = getReserveRequest();
        willThrow(UnAuthorizedException.class).given(reservationValidator).validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt());

        // When, Then
        assertThrows(UnAuthorizedException.class, () -> reserveTableService.reserve(memberId, reserveReq));
    }



    private ReserveReq getReserveRequest() {
        return ReserveReq.builder()
                .projectTableId(3L)
                .startAt(LocalDateTime.now().plusDays(1L))
                .endAt(LocalDateTime.now().plusDays(1L).plusHours(1L))
                .build();
    }


}