package com.cse.cseprojectroommanagementserver.domain.violation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;

import java.util.List;
import java.util.Optional;

public interface ViolationSearchableRepository {
    Optional<List<Violation>> findNotPenalizedViolationsByMemberId(Long memberId);
    Long countNotPenalizedViolationsByMemberId(Long memberId);
}
