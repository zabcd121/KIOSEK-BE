package com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyResponse;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyResponse.*;

public interface PenaltySearchableRepository {

    boolean existsByMemberId(Long memberId);
    Optional<Penalty> findInProgressByMemberId(Long memberId);

    Optional<List<Penalty>> findAllByMemberId(Long memberId);

    Page<SearchPenaltyByPagingResponse> findAllByConditionAndPageable(PenaltySearchCondition condition, Pageable pageable);
}
