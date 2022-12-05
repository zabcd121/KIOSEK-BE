package com.cse.cseprojectroommanagementserver.domain.member.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;

import java.util.Optional;

public interface MemberSearchableRepository {
    Optional<Member> findByAccountQRContents(String accountQRContents);
    Optional<Member> findByAccountLoginId(String loginId);

    Member findMemberWithAccountQRByMemberId(Long memberId);
}
