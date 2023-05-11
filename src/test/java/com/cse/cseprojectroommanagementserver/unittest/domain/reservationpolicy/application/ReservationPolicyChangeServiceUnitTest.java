package com.cse.cseprojectroommanagementserver.unittest.domain.reservationpolicy.application;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxCountPerDay;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxHourPerOnce;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxPeriod;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicyRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.exception.NotExistsReservationPolicyException;
import com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationPolicyChangeServiceUnitTest {

    @InjectMocks
    ReservationPolicyChangeService reservationPolicyChangeService;

    @Mock
    ReservationPolicyRepository reservationPolicyRepository;

    ReservationPolicyChangeReq changeReq;

    @BeforeEach
    void setUp() {
        changeReq = ReservationPolicyChangeReq.builder()
                .reservationPolicyId(1L)
                .reservationMaxHourPerOnce(6)
                .reservationMaxCountPerDay(2)
                .reservationMaxPeriod(3)
                .build();
    }

    /**
     * 테스트 케이스
     * C1. 예약 정책 변경 성공
     * C2. 예약 정책 변경 실패
         * C2-01. 예약 정책 변경 실패 - request reservationPolicyId에 해당하는 정책이 존재하지 않음
     */

    @Test
    @DisplayName("C1. 예약 정책 변경 성공")
    void 예약정책변경_성공() {
        // Given
        ReservationPolicy existingReservationPolicy = ReservationPolicy.builder()
                .reservationPolicyId(1L)
                .reservationMaxHourPerOnce(new ReservationMaxHourPerOnce(4))
                .reservationMaxCountPerDay(new ReservationMaxCountPerDay(1))
                .reservationMaxPeriod(new ReservationMaxPeriod(2))
                .appliedStatus(AppliedStatus.CURRENTLY)
                .build();
        given(reservationPolicyRepository.findById(changeReq.getReservationPolicyId())).willReturn(Optional.of(existingReservationPolicy));

        ReservationPolicy newReservationPolicy = ReservationPolicy.createReservationPolicy(
                changeReq.getReservationMaxHourPerOnce(), changeReq.getReservationMaxCountPerDay(), changeReq.getReservationMaxPeriod()
        );
        given(reservationPolicyRepository.save(any())).willReturn(newReservationPolicy);

        // When
        reservationPolicyChangeService.changeReservationPolicy(changeReq);

        // Then
        assertEquals(AppliedStatus.DEPRECATED, existingReservationPolicy.getAppliedStatus());
        assertEquals(AppliedStatus.CURRENTLY, newReservationPolicy.getAppliedStatus());
    }
    
    @Test
    @DisplayName("C2-01. 예약 정책 변경 실패 - request reservationPolicyId에 해당하는 정책이 존재하지 않음")
    void 예약정책변경_실패_요청들어온_기존예약정책ID가_존재X() {
        // Given
        given(reservationPolicyRepository.findById(changeReq.getReservationPolicyId())).willReturn(Optional.ofNullable(null));

        // When, Then
        assertThrows(NotExistsReservationPolicyException.class, () -> reservationPolicyChangeService.changeReservationPolicy(changeReq));
    }
}