package com.cse.cseprojectroommanagementserver.domain.penalty.application;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltySearchService {

    private final PenaltySearchableRepository penaltySearchableRepository;

    public List<PenaltyLogRes> searchMemberPenaltyLogList(Long memberId) {
        List<Penalty> penaltyList = penaltySearchableRepository.findAllByMemberId(memberId).orElseGet(() -> new ArrayList<>());

        List<PenaltyLogRes> penaltyLogResList = new ArrayList<>();
        for (Penalty penalty : penaltyList) {
            penaltyLogResList.add(PenaltyLogRes.of(penalty));
        }

        return penaltyLogResList;
    }

    public Page<SearchPenaltyByPagingRes> searchPenaltyListByConditionAndPageable(PenaltySearchCondition condition, Pageable pageable) {
        return penaltySearchableRepository.findAllByConditionAndPageable(condition, pageable);
    }

}
