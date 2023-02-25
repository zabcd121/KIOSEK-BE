package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.SignupRepository;
import com.cse.cseprojectroommanagementserver.domain.member.exception.EmailDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.LoginIdDuplicatedException;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class SignupServiceMockTest {
    @InjectMocks SignupService signupService;

    @Mock SignupRepository signupRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock QRGenerator qrGenerator;

    SignupReq signupReq;

    Member member;

    Account account;

    QRImage qrImage;
    AccountQR accountQR;

    @BeforeEach
    void setUp() {
        qrImage = QRImage.builder().fileLocalName("localName").fileOriName("account_qr").fileUrl("/Users/khs/Documents/images").content("randomContent").build();

        signupReq = SignupReq.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("example@kumoh.ac.kr")
                .name("소공이")
                .build();

        account = Account.builder()
                .loginId(signupReq.getLoginId())
                .password(signupReq.getPassword())
                .build();

        member = Member.builder()
                .account(account)
                .name(signupReq.getName())
                .email(signupReq.getEmail())
                .roleType(RoleType.ROLE_MEMBER)
                .accountQR(AccountQR.builder().qrImage(qrImage).build())
                .build();

        member.getAccountQR().setMember(member);
    }

    /** 테스트 케이스
     * C1. 회원가입 성공
     * C2. 회원가입 실패
     */

    @Test
    @DisplayName("C1. 회원가입 성공")
    void 회원가입_성공() {
        //given
        given(qrGenerator.createAccountQRCodeImage()).willReturn(qrImage);
        given(signupRepository.save(any())).willReturn(member);

        //when
        signupService.signup(signupReq);

        //then
        Member expectedMember = member;
        Member createdMember = Member.createMember(account, signupReq.getEmail(), signupReq.getName(), qrImage);

        assertEquals(createdMember.getAccount(), expectedMember.getAccount());
        assertEquals(createdMember.getEmail(), expectedMember.getEmail());
        assertEquals(createdMember.getName(), expectedMember.getName());
        assertEquals(createdMember.getAccountQR().getQrImage(), expectedMember.getAccountQR().getQrImage());

        then(qrGenerator).should(times(1)).createAccountQRCodeImage();
        then(signupRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("중복 아이디가 존재하는 경우 True를 반환한다.")
    void isDuplicatedLoginId() {
        //given
        given(signupRepository.existsByAccountLoginId(any())).willReturn(true);

        //when
        signupService.isDuplicatedLoginId(member.getLoginId());

        //then
        assertThrows(LoginIdDuplicatedException.class, () -> signupService.isDuplicatedLoginId(member.getLoginId()));
    }

    @Test
    @DisplayName("중복 아이디가 존재하지 않는 경우 False를 반환한다.")
    void isNotDuplicatedLoginId() {
        //given
        given(signupRepository.existsByAccountLoginId(any())).willReturn(false);

        //then
        assertFalse(signupService.isDuplicatedLoginId(member.getLoginId()));
    }

    @Test
    @DisplayName("중복 이메일이 존재하는 경우 DuplicatedEmailException 예외를 반환한다.")
    void isDuplicatedEmail() {
        //given
        given(signupRepository.existsByEmail(any())).willReturn(true);

        //then
        assertThrows(EmailDuplicatedException.class, () -> signupService.isDuplicatedEmail(member.getEmail()));
    }

    @Test
    @DisplayName("중복 이메일이 존재하지 않는 경우 false를 반환한다.")
    void isNotDuplicatedEmail() {
        //given
        given(signupRepository.existsByEmail(any())).willReturn(false);

        //then
        assertFalse(signupService.isDuplicatedEmail(member.getEmail()));
    }

    @Test
    @DisplayName("회원 가입시 로직사이에 생성되는 회원과 실제 DB에 저장되어 반환되는 회원 인스턴스가 동일한 정보를 가지고 있는지 확인한다.")
    void signup() throws IOException, WriterException {
        //given
        given(qrGenerator.createAccountQRCodeImage()).willReturn(qrImage);
        given(signupRepository.save(any())).willReturn(member);

        //when
        signupService.signup(signupReq);

        //then
        Member expectedMember = member;
        Member createdMember = Member.createMember(account, signupReq.getEmail(), signupReq.getName(), qrImage);

        assertEquals(createdMember.getAccount(), expectedMember.getAccount());
        assertEquals(createdMember.getEmail(), expectedMember.getEmail());
        assertEquals(createdMember.getName(), expectedMember.getName());
        assertEquals(createdMember.getAccountQR().getQrImage(), expectedMember.getAccountQR().getQrImage());

        then(qrGenerator).should(times(1)).createAccountQRCodeImage();
        then(signupRepository).should(times(1)).save(any());

    }
}
