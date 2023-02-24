package com.cse.cseprojectroommanagementserver.domain.penalty.repository;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.QMember.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.QPenalty.*;
import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyResDto.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class PenaltySearchRepository implements PenaltySearchableRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public boolean existsByMemberId(Long memberId) {
        Integer count = queryFactory
                .selectOne()
                .from(penalty)
                .where(penalty.member.memberId.eq(memberId)
                        .and(penalty.startDate.before(LocalDate.now())).and(penalty.endDate.after(LocalDate.now())))
                .fetchFirst();

        return count != null ? true : false;

    }

    @Override
    public Optional<Penalty> findInProgressByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(penalty)
                .where(penalty.member.memberId.eq(memberId)
                        .and(penalty.startDate.loe(LocalDate.now())
                                .and(penalty.endDate.goe(LocalDate.now()))))
                .fetchOne()
        );
    }

    @Override
    public Optional<List<Penalty>> findAllByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(penalty)
                        .where(penalty.member.memberId.eq(memberId))
                        .fetch()
        );
    }

    @Override
    public Page<SearchPenaltyByPagingRes> findAllByConditionAndPageable(PenaltySearchCondition condition, Pageable pageable) {
        List<SearchPenaltyByPagingRes> content = queryFactory
                .select(Projections.fields(SearchPenaltyByPagingRes.class,
                        Projections.fields(PenaltyLogRes.class, penalty.penaltyId, penalty.startDate, penalty.endDate, penalty.description),
                        Projections.fields(MemberSimpleInfoRes.class, penalty.member.memberId, penalty.member.account.loginId, penalty.member.name)
                ))
                .from(penalty)
                .join(penalty.member, member)
                .where(
                        memberNameEq(condition.getMemberName()),
                        loginIdEq(condition.getLoginId())
                )
                .orderBy(penalty.startDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(penalty.count())
                .from(penalty)
                .where(
                        memberNameEq(condition.getMemberName()),
                        loginIdEq(condition.getLoginId())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }

    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? penalty.member.name.eq(memberName) : null;
    }

    private BooleanExpression loginIdEq(String loginId) {
        return hasText(loginId) ? penalty.member.account.loginId.eq(loginId) : null;
    }

}
