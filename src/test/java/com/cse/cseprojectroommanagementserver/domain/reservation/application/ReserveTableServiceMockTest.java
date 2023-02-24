package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.DisabledTableException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.DuplicatedReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.PenaltyMemberReserveFailException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.ReservationQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxPeriodEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedMaxTimeEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.ExceedTodaysMaxCountEnableReservationException;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveTableServiceMockTest {

    @InjectMocks ReserveTableService reserveTableService;

    @Mock ReservationVerifiableRepository reservationVerifiableRepository;
    @Mock ReservationRepository reservationRepository;
    @Mock PenaltySearchableRepository penaltySearchRepository;
    @Mock ReservationPolicySearchableRepository reservationPolicySearchableRepository;
    @Mock MemberRepository memberRepository;
    @Mock ProjectTableRepository projectTableRepository;
    @Mock TableDeactivationSearchableRepository tableDeactivationSearchableRepository;

    @Mock QRGenerator qrGenerator;

    ReservationPolicy reservationPolicy;
    static final int DAYS_OF_WEEK = 7;

    /** 테스트 케이스
     * C1. 예약 성공 - 모두 통과
     * C2. 예약 실패
         * C2-01. 예약 실패 - 제재중인 회원
         * C2-02. 예약 실패 - 비활성화된 테이블 선택 (정상 회원, 중복 예약X)
         * C2-03. 예약 실패 - 중복된 예약 (정상 회원, 활성화된 테이블)
         *
         * C2-04. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 PASS
         * C2-05. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS
         * C2-06. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL
         * C2-07. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL
         * C2-08. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS
         * C2-09. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL
         * C2-10. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL
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

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        given(penaltySearchRepository.existsByMemberId(reserveReq.getMemberId())).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);
        given(reservationVerifiableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);

        given(reservationPolicySearchableRepository.findCurrentlyPolicy()).willReturn(reservationPolicy);
        given(reservationVerifiableRepository.countCreatedReservationForTodayBy(reserveReq.getMemberId())).willReturn(reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L);

        given(memberRepository.getReferenceById(reserveReq.getMemberId())).willReturn(Member.builder().memberId(reserveReq.getMemberId()).build());
        given(projectTableRepository.getReferenceById(reserveReq.getProjectTableId())).willReturn(ProjectTable.builder().tableId(reserveReq.getProjectTableId()).build());
        Reservation reservation = Reservation.builder().build();
        given(reservationRepository.save(any())).willReturn(reservation);

        // When
        reserveTableService.reserve(reserveReq);

        // Then
        assertDoesNotThrow(() -> ReservationQRNotCreatedException.class);
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("C2-01. 예약 실패 - 제재중인 회원")
    void 예약_실패_제재중인회원() {
        // Given
        ReserveReq reserveReq = getReserveRequest();
        given(penaltySearchRepository.existsByMemberId(reserveReq.getMemberId())).willReturn(true);

        // When, Then
        assertThrows(PenaltyMemberReserveFailException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-02. 예약 실패 - 비활성화된 테이블 선택 (정상 회원, 중복 예약X)")
    void 예약_실패_비활성화된테이블() {
        // Given
        ReserveReq reserveReq = getReserveRequest();
        given(penaltySearchRepository.existsByMemberId(reserveReq.getMemberId())).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(true);


        // When, Then
        assertThrows(DisabledTableException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-03. 예약 실패 - 중복된 예약 (정상 회원, 활성화된 테이블)")
    void 예약_실패_중복된예약() {
        // Given
        ReserveReq reserveReq = getReserveRequest();
        given(penaltySearchRepository.existsByMemberId(reserveReq.getMemberId())).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);
        given(reservationVerifiableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(true);


        // When, Then
        assertThrows(DuplicatedReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-04. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 PASS")
    void 예약_실패_예약정책위반_최대예약가능시간초과() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.now().plusDays(1L);
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedMaxTimeEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-05. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS")
    void 예약_실패_예약정책위반_최대예약가능시간초과_하루예약가능횟수초과() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.now().plusDays(1L);
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedMaxTimeEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-06. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_최대예약가능시간초과_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더, 현재 예약 불가능한 날짜임

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedMaxTimeEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-07. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_최대예약가능시간초과_하루예약가능횟수초과_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedMaxTimeEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-08. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS")
    void 예약_실패_예약정책위반_하루예약가능횟수초과() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()); // 최대예약가능시간보다 1시간 더

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedTodaysMaxCountEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-09. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(5, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()); // 최대예약가능시간보다 1시간 더

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedMaxPeriodEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }

    @Test
    @DisplayName("C2-10. 예약 실패 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_하루예약가능횟수초과_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(5, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()); // 최대예약가능시간보다 1시간 더

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(ExceedTodaysMaxCountEnableReservationException.class, () -> reserveTableService.reserve(reserveReq));
    }


    private void givenTemplateOfReservationPolicyViolationTest(ReserveReq reserveReq, Long todayReservationCountByMember) {
        given(penaltySearchRepository.existsByMemberId(reserveReq.getMemberId())).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);
        given(reservationVerifiableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);

        given(reservationPolicySearchableRepository.findCurrentlyPolicy()).willReturn(reservationPolicy);
        given(reservationVerifiableRepository.countCreatedReservationForTodayBy(reserveReq.getMemberId())).willReturn(todayReservationCountByMember);
    }

    private ReserveReq getReserveRequest() {
        return ReserveReq.builder()
                .projectTableId(3L)
                .memberId(3L)
                .startAt(LocalDateTime.now().plusDays(1L))
                .endAt(LocalDateTime.now().plusDays(1L).plusHours(1L))
                .build();
    }


}