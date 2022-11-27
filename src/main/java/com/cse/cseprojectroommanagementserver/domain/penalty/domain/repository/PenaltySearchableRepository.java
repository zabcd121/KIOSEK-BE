package com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository;

public interface PenaltySearchableRepository {

    public boolean existsByMemberId(Long memberId);
}
