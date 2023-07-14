package com.cse.cseprojectroommanagementserver.domain.reservation.domain.service;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReserveTableService;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReservationValidatorUnitTest {

    @InjectMocks
    ReservationValidator reservationValidator;

    @Mock
    ReservationVerifiableRepository reservationVerifiableRepository;
    @Mock
    PenaltySearchableRepository penaltySearchRepository;
    @Mock
    ReservationPolicySearchableRepository reservationPolicySearchableRepository;
    @Mock
    TableDeactivationSearchableRepository tableDeactivationSearchableRepository;

    @Mock
    QRGenerator qrGenerator;

    ReservationPolicy reservationPolicy;
    static final int DAYS_OF_WEEK = 7;

    /** 테스트 케이스
     * C1. 예약 검증 성공 - 모두 통과
     * C2. 예약 검증 오류
     * C2-01. 예약 검증 오류 - 제재중인 회원
     * C2-02. 예약 검증 오류 - 비활성화된 테이블 선택 (정상 회원, 중복 예약X)
     * C2-03. 예약 검증 오류 - 중복된 예약 (정상 회원, 활성화된 테이블)
     * C2-04. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 PASS
     * C2-05. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS
     * C2-06. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL
     * C2-07. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL
     * C2-08. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS
     * C2-09. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL
     * C2-10. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL
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
    @DisplayName("C1. 예약 검증 성공 - 모두 통과")
    void 예약_성공() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour());
        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        given(penaltySearchRepository.existsByMemberId(memberId)).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);
        given(reservationVerifiableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);

        given(reservationPolicySearchableRepository.findCurrentlyPolicy()).willReturn(reservationPolicy);
        given(reservationVerifiableRepository.countCreatedReservationForTodayBy(memberId)).willReturn(reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L);

        Reservation savedReservation = Reservation.builder().build();

        // When
        reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt());

        // Then
        assertDoesNotThrow(() -> UnAuthorizedException.class);
        assertDoesNotThrow(() -> BusinessRuleException.class);
        assertDoesNotThrow(() -> DuplicationException.class);
        assertDoesNotThrow(() -> InvalidInputException.class);
    }

    @Test
    @DisplayName("C2-01. 예약 검증 오류 - 제재중인 회원")
    void 예약_실패_제재중인회원() {
        // Given
        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = getReserveRequest();
        given(penaltySearchRepository.existsByMemberId(memberId)).willReturn(true);
        // When, Then
        assertThrows(UnAuthorizedException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-02. 예약 검증 오류 - 비활성화된 테이블 선택 (정상 회원, 중복 예약X)")
    void 예약_실패_비활성화된테이블() {
        // Given
        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = getReserveRequest();
        given(penaltySearchRepository.existsByMemberId(memberId)).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(true);


        // When, Then
        assertThrows(BusinessRuleException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-03. 예약 검증 오류 - 중복된 예약 (정상 회원, 활성화된 테이블)")
    void 예약_실패_중복된예약() {
        // Given
        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = getReserveRequest();
        given(penaltySearchRepository.existsByMemberId(memberId)).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);
        given(reservationVerifiableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(true);


        // When, Then
        assertThrows(DuplicationException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-04. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 PASS")
    void 예약_실패_예약정책위반_최대예약가능시간초과() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.now().plusDays(1L);
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-05. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS")
    void 예약_실패_예약정책위반_최대예약가능시간초과_하루예약가능횟수초과() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.now().plusDays(1L);
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-06. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_최대예약가능시간초과_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더, 현재 예약 불가능한 날짜임

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-07. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 FAIL, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_최대예약가능시간초과_하루예약가능횟수초과_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()+1); // 최대예약가능시간보다 1시간 더

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-08. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 PASS")
    void 예약_실패_예약정책위반_하루예약가능횟수초과() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()); // 최대예약가능시간보다 1시간 더

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-09. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 PASS, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(5, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()); // 최대예약가능시간보다 1시간 더

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue() - 1L;

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }

    @Test
    @DisplayName("C2-10. 예약 검증 오류 - 예약 정책 위반 - 최대 예약 가능 시간 PASS, 하루 예약 가능 횟수 FAIL, 예약 가능한 날짜 FAIL")
    void 예약_실패_예약정책위반_하루예약가능횟수초과_예약불가능한날짜() {
        // Given
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * DAYS_OF_WEEK + 1), LocalTime.of(5, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour()); // 최대예약가능시간보다 1시간 더

        Long memberId = 3L;
        ReservationReqDto.ReserveReq reserveReq = ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();
        long todayReservationCountByMember = reservationPolicy.getReservationMaxCountPerDay().getMaxCount().longValue(); // 이미 최대횟수만큼 예약함.

        givenTemplateOfReservationPolicyViolationTest(memberId, reserveReq, todayReservationCountByMember);

        // When, Then
        assertThrows(PolicyInfractionException.class, () -> reservationValidator.validate(memberId, reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt()));
    }


    private void givenTemplateOfReservationPolicyViolationTest(Long memberId, ReservationReqDto.ReserveReq reserveReq, Long todayReservationCountByMember) {
        given(penaltySearchRepository.existsByMemberId(memberId)).willReturn(false);
        given(tableDeactivationSearchableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);
        given(reservationVerifiableRepository.existsBy(reserveReq.getProjectTableId(), reserveReq.getStartAt(), reserveReq.getEndAt())).willReturn(false);

        given(reservationPolicySearchableRepository.findCurrentlyPolicy()).willReturn(reservationPolicy);
        given(reservationVerifiableRepository.countCreatedReservationForTodayBy(memberId)).willReturn(todayReservationCountByMember);
    }

    private ReservationReqDto.ReserveReq getReserveRequest() {
        return ReservationReqDto.ReserveReq.builder()
                .projectTableId(3L)
                .startAt(LocalDateTime.now().plusDays(1L))
                .endAt(LocalDateTime.now().plusDays(1L).plusHours(1L))
                .build();
    }

}