package com.cse.cseprojectroommanagementserver.unittest.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationCancelService;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessRuleException;
import com.cse.cseprojectroommanagementserver.global.error.exception.NotFoundException;
import com.cse.cseprojectroommanagementserver.global.error.exception.UnAuthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationCancelServiceUnitTest {

    @InjectMocks
    ReservationCancelService reservationCancelService;

    @Mock
    ReservationSearchableRepository reservationSearchableRepository;

    /** 테스트 케이스
     * C1. 예약취소 성공
     * C2. 예약취소 실패
     * C2-01. 예약취소 실패 - 존재하지 않는 예약 ID
     * C2-02. 예약취소 실패 - 예약상태가 취소됨 상태 (예약상태가 예약완료여야만 취소가 가능함)
     * C2-03. 예약취소 실패 - 예약상태가 사용중 상태
     * C2-04. 예약취소 실패 - 예약상태가 미사용 상태
     * C2-05. 예약취소 실패 - 예약상태가 반납대기중 상태
     * C2-06. 예약취소 실패 - 예약상태가 미반납 상태
     * C2-07. 예약취소 실패 - 예약상태가 반납완료 상태
     */

    @Test
    @DisplayName("C1. 예약취소 성공")
    void 예약취소_성공() {
        // Given
        Long reqReservationId = 1L;
        Long memberId = 3L;
        Reservation findReservation = getReservationByReservationStatus(memberId, reqReservationId, RESERVATION_COMPLETED);
        given(reservationSearchableRepository.findByReservationId(reqReservationId)).willReturn(Optional.of(findReservation));

        // When
        reservationCancelService.cancelReservation(memberId, reqReservationId);

        // Then
        assertEquals(CANCELED, findReservation.getReservationStatus());
    }

    @Test
    @DisplayName("C2-01. 예약취소 실패 - 존재하지 않는 예약 ID")
    void 예약취소_실패_존재하지_않는_예약_ID() {
        // Given
        Long reqReservationId = 1L;
        Long memberId = 3L;
        given(reservationSearchableRepository.findByReservationId(reqReservationId)).willReturn(Optional.ofNullable(null));

        // When, Then
        assertThrows(NotFoundException.class, () -> reservationCancelService.cancelReservation(memberId, reqReservationId));
    }

    @Test
    @DisplayName("C2-02. 예약취소 실패 - 예약상태가 취소됨 상태 (예약상태가 예약완료여야만 취소가 가능함)")
    void 예약취소_실패_예약상태_취소됨() {
        templateOfReservationCancelTestWithReservationStatus(CANCELED);
    }

    @Test
    @DisplayName("C2-03. 예약취소 실패 - 예약상태가 사용중 상태")
    void 예약취소_실패_예약상태_사용중() {
        templateOfReservationCancelTestWithReservationStatus(IN_USE);
    }

    @Test
    @DisplayName("C2-04. 예약취소 실패 - 예약상태가 미사용 상태")
    void 예약취소_실패_예약상태_미사용() {
        templateOfReservationCancelTestWithReservationStatus(UN_USED);
    }

    @Test
    @DisplayName("C2-05. 예약취소 실패 - 예약상태가 반납대기중 상태")
    void 예약취소_실패_예약상태_반납대기중() {
        templateOfReservationCancelTestWithReservationStatus(RETURN_WAITING);
    }

    @Test
    @DisplayName("C2-06. 예약취소 실패 - 예약상태가 미반납 상태")
    void 예약취소_실패_예약상태_미반납() {
        templateOfReservationCancelTestWithReservationStatus(NOT_RETURNED);
    }

    @Test
    @DisplayName("C2-07. 예약취소 실패 - 예약상태가 반납완료 상태")
    void 예약취소_실패_예약상태_반납완료() {
        templateOfReservationCancelTestWithReservationStatus(RETURNED);
    }


    private void templateOfReservationCancelTestWithReservationStatus(ReservationStatus reservationStatus) {
        // Given
        Long reqReservationId = 1L;
        Long memberId = 3L;
        Reservation findReservation = getReservationByReservationStatus(memberId, reqReservationId, reservationStatus);

        given(reservationSearchableRepository.findByReservationId(reqReservationId)).willReturn(Optional.of(findReservation));

        // When, Then
        assertThrows(BusinessRuleException.class, () -> reservationCancelService.cancelReservation(memberId, reqReservationId));
    }

    private Reservation getReservationByReservationStatus(Long memberId, Long reservationId, ReservationStatus reservationStatus) {
        return Reservation.builder()
                .reservationId(reservationId)
                .member(Member.builder().memberId(memberId).build())
                .reservationStatus(reservationStatus)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusHours(2))
                .projectTable(
                        ProjectTable.builder().tableName("A1")
                                .projectRoom(ProjectRoom.builder().buildingName("디지털관").roomName("D330").priority(1).build())
                                .build())
                .build();
    }

}