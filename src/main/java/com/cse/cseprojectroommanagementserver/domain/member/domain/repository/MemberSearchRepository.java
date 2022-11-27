package com.cse.cseprojectroommanagementserver.domain.member.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSearchRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccountLoginId(String loginId);
}
