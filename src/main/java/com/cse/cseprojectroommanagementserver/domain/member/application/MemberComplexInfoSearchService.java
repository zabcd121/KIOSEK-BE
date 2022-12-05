package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationSearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberComplexInfoSearchService {

    private final MemberSearchableRepository memberSearchableRepository;
    private final ViolationSearchableRepository violationSearchableRepository;
    private final PenaltySearchableRepository penaltySearchableRepository;
    private final ReservationSearchableRepository reservationSearchableRepository;


    public MemberComplexInfoResponse searchMemberComplexInfo(Long memberId) {
        Member member = memberSearchableRepository.findMemberWithAccountQRByMemberId(memberId);
        Long violationsCount = violationSearchableRepository.countNotPenalizedViolationsByMemberId(memberId);
        Penalty penalty = penaltySearchableRepository.findInProgressByMemberId(memberId).orElseGet(() -> null);
        Long pastReservationsCount = reservationSearchableRepository.countPastReservations(memberId);

        return new MemberComplexInfoResponse().of(member.getAccountQR(), violationsCount, penalty, pastReservationsCount);
    }

}
