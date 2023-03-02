package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberSetUp {

    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(String loginId, String password, String email, String name) {
        return memberRepository.save(
                Member.createMember(
                        Account.builder().loginId(loginId).password(password).build(),
                        email, name,
                        QRImage.builder().fileOriName(loginId).fileLocalName(loginId).fileUrl("/accounts/2023/03/1").content("!@#ASdasd1!").build()
                )
        );
    }
}
