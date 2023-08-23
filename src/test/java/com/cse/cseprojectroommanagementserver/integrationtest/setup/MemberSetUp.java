package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType.*;

@Component
public class MemberSetUp {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSearchableRepository memberSearchableRepository;

    @Autowired
    EntityManager em;

    @Autowired
    private AES256 aes256;

    public Member saveMember(String loginId, String password, String email, String name) {
        return memberRepository.save(
                Member.createMember(
                        Account.builder().loginId(aes256.encrypt(loginId)).password(password).build(),
                        aes256.encrypt(email), name,
                        QRImage.builder().fileOriName(loginId).fileLocalName(loginId).fileUrl("/accounts/2023/03/1").content("!@#ASdasd1!").build()
                )
        );
    }

    public Member saveAdmin(String loginId, String password, String email, String name) {
        return memberRepository.save(
                Member.builder()
                        .account(Account.builder().loginId(aes256.encrypt(loginId)).password(password).build())
                        .email(aes256.encrypt(email))
                        .roleType(ROLE_ADMIN)
                        .name(name)
                        .build()

        );
    }

    public List<Member> findMembersWithOffsetAndLimit(int offset, int limit) {
        return em.createQuery("select m from Member m", Member.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Member findMember(String loginId) {
        return memberSearchableRepository.findByAccountLoginId(aes256.encrypt(loginId)).get();
    }
}
