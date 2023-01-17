package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.repository;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.repository.PenaltyPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.QPenaltyPolicy.*;

@Repository
@RequiredArgsConstructor
public class PenaltyPolicySearchRepository implements PenaltyPolicySearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public PenaltyPolicy findCurrentPenaltyPolicy() {
        return queryFactory
                .selectFrom(penaltyPolicy)
                .where(penaltyPolicy.appliedStatus.eq(AppliedStatus.CURRENTLY))
                .fetchOne();
    }
}
