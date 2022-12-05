package com.cse.cseprojectroommanagementserver.domain.violation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;

import java.util.List;

public interface ViolationSearchableRepository {
    List<Violation> findNotPenalizedViolationsByMemberId(Long memberId);
    Long countNotPenalizedViolationsByMemberId(Long memberId);
}
