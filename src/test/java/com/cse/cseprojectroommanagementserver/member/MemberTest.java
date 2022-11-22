package com.cse.cseprojectroommanagementserver.member;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberTest {

    final String loginId = "20230101";
    final String password = "example1234!";
    final String name = "소공이";
    final String email = "example@kumoh.ac.kr";

    Member member;
    Account account;
    QRImage QRImage;

    @BeforeEach
    void setUp() {
        account = Account.builder().loginId(loginId).password(password).build();
        QRImage = QRImage.builder().fileLocalName("localName").fileOriName("account_qr").fileUrl("/Users/khs/Documents/images").content("randomContent").build();

        member = Member.builder()
                .account(account)
                .name(name)
                .email(email)
                .roleType(RoleType.ROLE_MEMBER)
                .accountQR(AccountQR.builder().qrCodeImg(QRImage).build())
                .build();

        member.getAccountQR().setMember(member);
    }

    @DisplayName("Member.createMember() Member instance가 정상적으로 생성되는지와 AccountQR과의 양방향 관계 메서드가 제대로 작동했는지 확인한다.")
    @Test
    void createMember() {

        //when
        Member createdMember = Member.createMember(account, email, name, QRImage);

        //then
        assertEquals(member.getAccountQR().getMember().getAccount(), createdMember.getAccountQR().getMember().getAccount());

        assertEquals(member.getAccount(), createdMember.getAccount());
        assertEquals(member.getEmail(), createdMember.getEmail());
        assertEquals(member.getName(), createdMember.getName());
        assertEquals(member.getAccountQR().getQrCodeImg(), createdMember.getAccountQR().getQrCodeImg());
        assertEquals(member.getRoleType(), createdMember.getRoleType());

    }
}