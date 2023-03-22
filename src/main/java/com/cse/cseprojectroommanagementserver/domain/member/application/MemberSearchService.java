package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationSearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberSearchableRepository memberSearchableRepository;
    private final ViolationSearchableRepository violationSearchableRepository;
    private final PenaltySearchableRepository penaltySearchableRepository;
    private final ReservationSearchableRepository reservationSearchableRepository;


    public MemberComplexInfoRes searchMyPageInfo(Long memberId) {
        Member member = memberSearchableRepository.findMemberWithAccountQRByMemberId(memberId);
        Long violationsCount = violationSearchableRepository.countNotPenalizedViolationsByMemberId(memberId);
        Long pastReservationsCount = reservationSearchableRepository.countPastReservations(memberId);


        List<Penalty> penaltyList = penaltySearchableRepository.findAllByMemberId(memberId).orElseGet(() -> null);
        int penaltyCount = 0;
        Penalty currentPenalty = null;
        if(penaltyList != null) {
            penaltyCount = penaltyList.size();
            currentPenalty = penaltyList.stream()
                    .filter(penalty ->
                            (penalty.getStartDt().isBefore(LocalDate.now()) || penalty.getStartDt().isEqual(LocalDate.now()))
                            && (penalty.getEndDt().isAfter(LocalDate.now()) || penalty.getEndDt().isEqual(LocalDate.now())))
                    .findFirst().orElseGet(() -> null);
        }
        

        return new MemberComplexInfoRes().of(member.getAccountQR(), violationsCount, currentPenalty, pastReservationsCount, penaltyCount);
    }

}
