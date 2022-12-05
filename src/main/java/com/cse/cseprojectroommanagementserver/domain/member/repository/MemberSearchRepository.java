package com.cse.cseprojectroommanagementserver.domain.member.repository;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.QAccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.QMember;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.QAccountQR.*;
import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.QMember.*;

@Repository
@RequiredArgsConstructor
public class MemberSearchRepository implements MemberSearchableRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Member> findByAccountQRContents(String accountQRContents) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .join(member.accountQR, accountQR)
                        .on(accountQR.qrImage.content.eq(accountQRContents))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findByAccountLoginId(String loginId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.account.loginId.eq(loginId))
                        .fetchOne()
        );
    }

    @Override
    public Member findMemberWithAccountQRByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(member)
                .join(member.accountQR, accountQR).fetchJoin()
                .where(member.memberId.eq(memberId))
                .fetchOne();
    }


}
