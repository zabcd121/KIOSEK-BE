package com.cse.cseprojectroommanagementserver.domain.reservation.application.validator;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessRuleException;
import com.cse.cseprojectroommanagementserver.global.error.exception.DuplicationException;
import com.cse.cseprojectroommanagementserver.global.error.exception.InvalidInputException;
import com.cse.cseprojectroommanagementserver.global.error.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationValidator {

    private final ReservationVerifiableRepository reservationVerifiableRepository;
    private final PenaltySearchableRepository penaltySearchRepository;
    private final ReservationPolicySearchableRepository reservationPolicySearchableRepository;
    private final TableDeactivationSearchableRepository tableDeactivationSearchableRepository;

    public void validate(Long memberId, Long projectTableId, LocalDateTime startAt, LocalDateTime endAt) {
        if (!isPenaltyMember(memberId)
                && !isDisabledTable(projectTableId, startAt, endAt)
                && !isDuplicatedReservation(projectTableId, startAt, endAt)
                && !isEndAtAfterStartAt(startAt, endAt)) {
            ReservationPolicy reservationPolicy = findReservationPolicy();
            //오늘 이 회원이 예약을 실행한 횟수룰 가져옴
            Long countTodayMemberCreatedReservation = getCountTodayMemberCreatedReservation(memberId);
            reservationPolicy.verifyReservation(startAt, endAt, countTodayMemberCreatedReservation);
        }
    }


    private boolean isPenaltyMember(Long memberId) {
        if (penaltySearchRepository.existsByMemberId(memberId)) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_PENALTY_MEMBER);
        }
        return false;
    }

    private boolean isDisabledTable(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        if (tableDeactivationSearchableRepository.existsBy(tableId, startAt, endAt)) {
            throw new BusinessRuleException(ErrorCode.DISABLED_TABLE);
        }
        return false;
    }

    private boolean isDuplicatedReservation(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        if (reservationVerifiableRepository.existsBy(tableId, startAt, endAt)) {
            throw new DuplicationException(ErrorCode.DUPLICATED_RESERVATION);
        }
        return false;
    }

    private boolean isEndAtAfterStartAt(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt)) {
            throw new InvalidInputException(ErrorCode.INVALID_VALUE_ENDAT);
        }
        return false;
    }

    private ReservationPolicy findReservationPolicy() {
        return reservationPolicySearchableRepository.findCurrentlyPolicy();
    }

    private Long getCountTodayMemberCreatedReservation(Long memberId) {
        return reservationVerifiableRepository.countCreatedReservationForTodayBy(memberId);
    }
}
