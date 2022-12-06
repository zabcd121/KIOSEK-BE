package com.cse.cseprojectroommanagementserver.domain.violation.repository;

import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ProcessingStatus;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.QViolation;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.model.Violation;
import com.cse.cseprojectroommanagementserver.domain.violation.domain.repository.ViolationSearchableRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.ProcessingStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.violation.domain.model.QViolation.*;


@Repository
@RequiredArgsConstructor
public class ViolationSearchRepository implements ViolationSearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<Violation>> findNotPenalizedViolationsByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .select(violation)
                        .from(violation)
                        .where(violation.targetMember.memberId.eq(memberId)
                                .and(violation.processingStatus.eq(NON_PENALIZED)))
                        .fetch()
        );
    }

    @Override
    public Long countNotPenalizedViolationsByMemberId(Long memberId) {
        return queryFactory
                .select(violation.count())
                .from(violation)
                .where(violation.targetMember.memberId.eq(memberId)
                        .and(violation.processingStatus.eq(NON_PENALIZED)))
                .fetchOne();
    }
}
