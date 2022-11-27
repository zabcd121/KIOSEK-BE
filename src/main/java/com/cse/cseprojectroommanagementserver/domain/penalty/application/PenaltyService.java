package com.cse.cseprojectroommanagementserver.domain.penalty.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltyUserCheckRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.PenaltyMemberReserveFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.RESERVATION_FAIL_PENALTY_USER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltyService {

    private final PenaltyUserCheckRepository penaltyUserRepository;
    
}
