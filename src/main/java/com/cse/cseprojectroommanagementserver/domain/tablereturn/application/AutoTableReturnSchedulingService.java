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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AutoTableReturnSchedulingService {

    private final ReservationSearchableRepository reservationSearchableRepository;
    private final TableReturnRepository tableReturnRepository;
    private final ViolationRepository violationRepository;
    private final ViolationSearchableRepository violationSearchableRepository;
    private final PenaltyPolicySearchableRepository penaltyPolicySearchableRepository;
    private final PenaltyRepository penaltyRepository;

    @Scheduled(cron = "0 20,50 * * * *")
    @Transactional
    public void autoTableReturn() {
        List<Reservation> reservationList = reservationSearchableRepository.findReturnWaitingReservations().orElseGet(null);
        List<TableReturn> tableReturnList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            tableReturnList.add(TableReturn.createAutoReturn(reservation));
        }
        tableReturnRepository.saveAll(tableReturnList);
        addViolationLog(reservationList, ViolationContent.NOT_RETURNED_CONTENT);
    }

    @Scheduled(cron = "10 20,50 * * * *")
    @Transactional
    public void autoCancelUnUsedReservation() {
        List<Reservation> unUsedReservationList = reservationSearchableRepository.findUnUsedReservations();
        for (Reservation unUsedReservation : unUsedReservationList) {
            log.debug("autoCancelUnUsedReservation 동작: reservation is unused");
            unUsedReservation.setReservationStatus(UN_USED);
        }
        addViolationLog(unUsedReservationList, ViolationContent.UN_USED_CONTENT);
    }

    /**
     * 종료시간이 끝날때까지 반납이 되지 않은 예약들에 대해서 반납 대기중 상태로 변경
     */
    @Scheduled(cron = "1 0,30 * * * *")
    @Transactional
    public void changeUsedReservationToReturnWaiting() {
        List<Reservation> reservationList = reservationSearchableRepository.findFinishedButInUseStatusReservations().orElseGet(null);
        for (Reservation reservation : reservationList) {
            reservation.setReservationStatus(RETURN_WAITING);
        }
    }

    private void addViolationLog(List<Reservation> reservationList, ViolationContent violationContent) {
        List<Violation> violationList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            violationList.add(Violation.createViolation(reservation, violationContent));
        }
        violationRepository.saveAllAndFlush(violationList);
        addPenalty(violationList);
    }

    private void addPenalty(List<Violation> violationList) {
        PenaltyPolicy penaltyPolicy = penaltyPolicySearchableRepository.findCurrentPenaltyPolicy();

        List<Penalty> penaltyList = new ArrayList<>();
        for (Violation violation : violationList) {
            List<Violation> violationListNotPenalizedByMember = violationSearchableRepository
                    .findNotPenalizedViolationsByMemberId(violation.getTargetMember().getMemberId())
                    .orElseGet(() -> new ArrayList<>());

            if (penaltyPolicy.isPenaltyTarget(violationListNotPenalizedByMember.size())) {
                Penalty savedPenalty = penaltyRepository.save(Penalty.createPenalty(violation.getTargetMember(), penaltyPolicy, violationListNotPenalizedByMember));
                if(savedPenalty!=null) {
                    for (Violation targetViolation : violationListNotPenalizedByMember) {
                        targetViolation.changePenalty(savedPenalty);
                    }
                    penaltyList.add(savedPenalty);
                }
            }
        }
    }
}
