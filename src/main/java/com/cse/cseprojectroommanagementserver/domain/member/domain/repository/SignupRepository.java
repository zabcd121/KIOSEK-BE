package com.cse.cseprojectroommanagementserver.domain.member.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SignupRepository extends JpaRepository<Member, Long>{


    boolean existsByAccountLoginId(String loginId);
    boolean existsByEmail(String email);
}
