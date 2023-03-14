package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType.*;

@Component
public class MemberSetUp {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSearchableRepository memberSearchableRepository;

    public Member saveMember(String loginId, String password, String email, String name) {
        return memberRepository.save(
                Member.createMember(
                        Account.builder().loginId(loginId).password(password).build(),
                        email, name,
                        QRImage.builder().fileOriName(loginId).fileLocalName(loginId).fileUrl("/accounts/2023/03/1").content("!@#ASdasd1!").build()
                )
        );
    }

    public Member saveAdmin(String loginId, String password) {
        return memberRepository.save(
                Member.builder()
                        .account(Account.builder().loginId(loginId).password(password).build())
                        .roleType(ROLE_ADMIN)
                        .build()

        );
    }

    public Member findMember(String loginId) {
        return memberSearchableRepository.findByAccountLoginId(loginId).get();
    }
}
