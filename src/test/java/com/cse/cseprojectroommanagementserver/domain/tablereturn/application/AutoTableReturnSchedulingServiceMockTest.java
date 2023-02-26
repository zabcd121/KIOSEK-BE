package com.cse.cseprojectroommanagementserver.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltyRepository;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.NumberOfSuspensionDay;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.ViolationCountToImposePenalty;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnRepository;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ViolationContent;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationRepository;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ProcessingStatus.NON_PENALIZED;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AutoTableReturnSchedulingServiceMockTest {

    @InjectMocks AutoTableReturnSchedulingService autoTableReturnSchedulingService;

    @Mock ReservationSearchableRepository reservationSearchableRepository;
    @Mock TableReturnRepository tableReturnRepository;
    @Mock ViolationRepository violationRepository;
    @Mock ViolationSearchableRepository violationSearchableRepository;
    @Mock PenaltyPolicySearchableRepository penaltyPolicySearchableRepository;
    @Mock PenaltyRepository penaltyRepository;

    /** 테스트 케이스
     * M1. 유예시간 후에도 미반납된 예약들 자동 반납 기능
         * M1-C1. 유예시간 후에도 미반납된 예약들 자동 반납 기능 성공
     * M2. 예약 자동 취소 기능
         * M2-C1. 예약 자동 취소 기능 성공
     * M3. 사용시간 종료후 반납 대기중 상태로 자동 변경
         * M3-C1. 사용시간 종료후 반납 대기중 상태로 자동 변경 성공
     */

    @Test
    @DisplayName("M1-C1. 유예시간 후에도 미반납된 예약들 자동 반납 기능 성공")
    void 자동반납_성공_유예시간후에미반납된예약() {
        // Given
        // 사용종료 후 반납대기중 상태인 예약리스트
        List<Reservation> returnWaitingReservationList = getReturnWaitingReservationList();
        given(reservationSearchableRepository.findReturnWaitingReservations()).willReturn(Optional.of(returnWaitingReservationList));

        List<TableReturn> autoTableReturnList = getAutoTableReturnList(returnWaitingReservationList);
        given(tableReturnRepository.saveAll(any())).willReturn(autoTableReturnList);

        // 반납 대기중 상태인 예약들을 위반
        List<Violation> violationList = getViolationListBy(returnWaitingReservationList);
        given(violationRepository.saveAllAndFlush(any())).willReturn(violationList);

        //최신 제재 정책 가져옴
        given(penaltyPolicySearchableRepository.findCurrentPenaltyPolicy()).willReturn(getCurrentPenaltyPolicy());

        for (Violation violation : violationList) {
            List<Violation> violationListByMember = getViolationListByMember(violation.getTargetMember(), violation.getTargetReservation()); // 현재 히원의 위반 내역과 그전에 처벌받지 않은 위반 내역을 함께 리스트로 가져옴
            given(violationSearchableRepository.findNotPenalizedViolationsByMemberId(violation.getTargetMember().getMemberId()))
                    .willReturn(Optional.of(violationListByMember));
        }

        given(penaltyRepository.saveAllAndFlush(any())).willReturn(new ArrayList<>());

        // When
        autoTableReturnSchedulingService.autoTableReturn();

        // Then
        Assertions.assertEquals(ReservationStatus.NOT_RETURNED, returnWaitingReservationList.get(0).getReservationStatus());
        Assertions.assertEquals(ReservationStatus.NOT_RETURNED, returnWaitingReservationList.get(1).getReservationStatus());
        then(tableReturnRepository).should(times(1)).saveAll(any());
        then(violationRepository).should(times(1)).saveAllAndFlush(any());
        then(penaltyRepository).should(times(1)).saveAllAndFlush(any());
    }

    private List<TableReturn> getAutoTableReturnList(List<Reservation> returnWaitingReservationList) {
        List<TableReturn> tableReturnList = new ArrayList<>();
        for (Reservation reservation : returnWaitingReservationList) {
            tableReturnList.add(TableReturn.createAutoReturn(reservation));
        }

        return tableReturnList;
    }

    private List<Reservation> getReturnWaitingReservationList() {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(getReturnWaitingReservationBy(1L, getMemberBy(1L)));
        reservationList.add(getReturnWaitingReservationBy(2L, getMemberBy(2L)));

        return reservationList;
    }

    private Reservation getReturnWaitingReservationBy(Long reservationId, Member member) {
        return Reservation.builder()
                .reservationId(reservationId)
                .member(member)
                .reservationStatus(ReservationStatus.RETURN_WAITING)
                .build();
    }

    private List<Violation> getViolationListBy(List<Reservation> returnWaitingReservationList) {
        List<Violation> violationList = new ArrayList<>();
        for (Reservation reservation : returnWaitingReservationList) {
            violationList.add(Violation.createViolation(reservation, ViolationContent.NOT_RETURNED_CONTENT));
        }

        return violationList;
    }

    private Member getMemberBy(Long memberId) {
        return Member.builder()
                .memberId(memberId)
                .build();
    }

    private PenaltyPolicy getCurrentPenaltyPolicy() {
        return PenaltyPolicy.builder()
                .penaltyPolicyId(1L)
                .violationCountToImposePenalty(new ViolationCountToImposePenalty(2))
                .numberOfSuspensionDay(new NumberOfSuspensionDay(2))
                .appliedStatus(AppliedStatus.CURRENTLY).build();
    }

    private List<Violation> getViolationListByMember(Member member, Reservation nowViolatedReservation) {
        List<Violation> violationList = new ArrayList<>();
        violationList.add(
                Violation.builder()
                        .targetMember(member)
                        .violationContent(ViolationContent.NOT_RETURNED_CONTENT)
                        .processingStatus(NON_PENALIZED)
                        .targetReservation(nowViolatedReservation)
                        .build()
        );
        violationList.add(
                Violation.builder()
                        .targetMember(member)
                        .violationContent(ViolationContent.UN_USED_CONTENT)
                        .processingStatus(NON_PENALIZED)
                        .targetReservation(
                                Reservation.builder()
                                        .member(member)
                                        .reservationStatus(ReservationStatus.UN_USED)
                                        .build())
                        .build()
        );

        return violationList;
    }
}