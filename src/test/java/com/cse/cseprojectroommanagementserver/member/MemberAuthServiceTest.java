package com.cse.cseprojectroommanagementserver.member;

import com.cse.cseprojectroommanagementserver.domain.member.application.MemberAuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.dto.request.SignupRequest;
import com.cse.cseprojectroommanagementserver.domain.member.exception.DuplicatedEmailException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.DuplicatedLoginIdException;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class MemberAuthServiceTest {

    @InjectMocks
    MemberAuthService memberAuthService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    QRGenerator qrGenerator;

    SignupRequest signupDto;

    Member member;

    Account account;

    Image image;
    AccountQR accountQR;

    @BeforeEach
    void setUp() {
//        when(passwordEncoder.encode(any())).thenReturn("ex1234!");
        image = Image.builder().fileLocalName("localName").fileOriName("account_qr").fileUrl("/Users/khs/Documents/images").content("randomContent").build();


        signupDto = SignupRequest.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("example@kumoh.ac.kr")
                .name("소공이")
                .build();

        account = Account.builder()
                .loginId(signupDto.getLoginId())
                .password(signupDto.getPassword())
                .build();

        member = Member.builder()
                .account(account)
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .roleType(RoleType.ROLE_MEMBER)
                .accountQR(AccountQR.builder().qrCodeImg(image).build())
                .build();
        member.getAccountQR().setMember(member);

    }

    @Test
    @DisplayName("중복 아이디가 존재하는 경우 DuplicatedEmailException 예외를 반환한다.")
    void isDuplicatedLoginId() {
        //given
        given(memberRepository.existsByAccountLoginId(any())).willReturn(true);

        //then
        assertThrows(DuplicatedLoginIdException.class, () -> memberAuthService.isDuplicatedLoginId(member.getLoginId()));
    }

    @Test
    @DisplayName("중복 아이디가 존재하지 않는 경우 false를 반환한다.")
    void isNotDuplicatedLoginId() {
        //given
        given(memberRepository.existsByAccountLoginId(any())).willReturn(false);

        //then
        assertFalse(memberAuthService.isDuplicatedLoginId(member.getLoginId()));
    }

    @Test
    @DisplayName("중복 이메일이 존재하는 경우 DuplicatedEmailException 예외를 반환한다.")
    void isDuplicatedEmail() {
        //given
        given(memberRepository.existsByEmail(any())).willReturn(true);

        //then
        assertThrows(DuplicatedEmailException.class, () -> memberAuthService.isDuplicatedEmail(member.getEmail()));
    }

    @Test
    @DisplayName("중복 이메일이 존재하지 않는 경우 false를 반환한다.")
    void isNotDuplicatedEmail() {
        //given
        given(memberRepository.existsByEmail(any())).willReturn(false);

        //then
        assertFalse(memberAuthService.isDuplicatedEmail(member.getEmail()));
    }

    @Test
    @DisplayName("회원 가입시 로직사이에 생성되는 회원과 실제 DB에 저장되어 반환되는 회원 인스턴스가 동일한 정보를 가지고 있는지 확인한다.")
    void signup() throws IOException, WriterException {
        //given
        given(qrGenerator.createAccountQRCodeImage(any())).willReturn(image);
        given(memberRepository.save(any())).willReturn(member);

        //when
        memberAuthService.signup(signupDto);

        //then
        Member expectedMember = member;
        Member createdMember = Member.createMember(account, signupDto.getEmail(), signupDto.getName(), image);

        assertEquals(createdMember.getAccount(), expectedMember.getAccount());
        assertEquals(createdMember.getEmail(), expectedMember.getEmail());
        assertEquals(createdMember.getName(), expectedMember.getName());
        assertEquals(createdMember.getAccountQR().getQrCodeImg(), expectedMember.getAccountQR().getQrCodeImg());

        then(qrGenerator).should(times(1)).createAccountQRCodeImage(any());
        then(memberRepository).should(times(1)).save(any());

    }

}