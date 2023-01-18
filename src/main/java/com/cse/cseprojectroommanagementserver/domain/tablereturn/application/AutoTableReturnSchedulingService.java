package com.cse.cseprojectroommanagementserver.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltyRepository;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnRepository;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ViolationContent;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationRepository;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationSearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AutoTableReturnSchedulingService {

    private final ReservationSearchableRepository reservationSearchRepository;
    private final TableReturnRepository tableReturnRepository;
    private final ViolationRepository violationRepository;
    private final ViolationSearchableRepository violationSearchRepository;
    private final PenaltyPolicySearchableRepository penaltyPolicySearchRepository;
    private final PenaltyRepository penaltyRepository;

    @Scheduled(cron = "0 20,50 * * * *")
    @Transactional
    public void autoTableReturn() {
        List<Reservation> reservationList = reservationSearchRepository.findReturnWaitingReservations().orElseGet(null);
        List<TableReturn> tableReturnList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            tableReturnList.add(TableReturn.createAutoReturn(reservation));
        }
        tableReturnRepository.saveAll(tableReturnList);
        addViolationLog(reservationList, ViolationContent.NOT_RETURNED);
    }


    public void addViolationLog(List<Reservation> reservationList, ViolationContent violationContent) {
        List<Violation> violationList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            violationList.add(Violation.createViolation(reservation, violationContent));
        }
        violationRepository.saveAllAndFlush(violationList);
        addPenalty(violationList);
    }

    /**
     * TODO
     * 쿼리수가 너무 많다. 성능 개선이 필요하다. 생각해보니 1개의 방에 6개의 테이블이 존재하는데 현재 총 2개의 방이 존재하니 0번~12번 쿼리가 호출된다. 괜찮을 수도 있을 것 같다.
     *
     * @param violationList
     */

    public void addPenalty(List<Violation> violationList) {
        PenaltyPolicy penaltyPolicy = penaltyPolicySearchRepository.findCurrentPenaltyPolicy();

        List<Penalty> penaltyList = new ArrayList<>();
        for (Violation violation : violationList) {
            List<Violation> violationListNotPenalizedByMember = violationSearchRepository
                    .findNotPenalizedViolationsByMemberId(violation.getTargetMember().getMemberId())
                    .orElseGet(() -> new ArrayList<>());

            if (penaltyPolicy.isPenaltyTarget(violationListNotPenalizedByMember.size())) {
                Penalty penalty = Penalty.createPenalty(violation.getTargetMember(), penaltyPolicy, violationListNotPenalizedByMember);
                penaltyList.add(penalty);
            }
        }
        penaltyRepository.saveAllAndFlush(penaltyList);
    }

    @Scheduled(cron = "10 20,50 * * * *")
    @Transactional
    public void autoCancelUnUsedReservation() {
        List<Reservation> unUsedReservationList = reservationSearchRepository.findUnUsedReservations();
        for (Reservation unUsedReservation : unUsedReservationList) {
            System.out.println("reservation to unused#############");
            unUsedReservation.changeReservationStatus(UN_USED);
        }
        addViolationLog(unUsedReservationList, ViolationContent.UN_USED);
    }

    /**
     * 종료시간이 끝날때까지 반납이 되지 않은 예약들에 대해서 반납 대기중 상태로 변경
     */
    @Scheduled(cron = "1 0,30 * * * *")
    @Transactional
    public void changeUsedReservationToReturnWaiting() {
        List<Reservation> reservationList = reservationSearchRepository.findFinishedButInUseStatusReservations().orElseGet(null);
        for (Reservation reservation : reservationList) {
            reservation.changeReservationStatus(RETURN_WAITING);
        }
    }
}
