package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.SignupRepository;
import com.cse.cseprojectroommanagementserver.domain.member.exception.AccountQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.EmailDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.LoginIdDuplicatedException;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.QRNotCreatedException;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SignupServiceUnitTest {
    @InjectMocks SignupService signupService;

    @Mock SignupRepository signupRepository;
    @Spy PasswordEncoder passwordEncoder;
    @Mock QRGenerator qrGenerator;

    SignupReq signupReq;

    @BeforeEach
    void setUp() {
        signupReq = SignupReq.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("example@kumoh.ac.kr")
                .name("소공이")
                .build();
    }

    /** 테스트 케이스
     * C1. 회원가입 성공
     * C2. 회원가입 실패
         * C2-01. 회원가입 실패 - 중복 아이디
         * C2-02. 회원가입 실패 - 중복 이메일
         * C2-03. 회원가입 실패 - QR 생성 실패
     */

    @Test
    @DisplayName("C1. 회원가입 성공")
    void 회원가입_성공() throws IOException, WriterException {
        //Given
        given(signupRepository.existsByAccountLoginId(signupReq.getLoginId())).willReturn(false);
        given(signupRepository.existsByEmail(signupReq.getEmail())).willReturn(false);

        QRImage qrImage = QRImage.builder().fileLocalName("localName").fileOriName("account_qr").fileUrl("/Users/khs/Documents/images").content("randomContent").build();
        given(qrGenerator.createAccountQRCodeImage()).willReturn(qrImage);

        Account account = Account.builder()
                .loginId(signupReq.getLoginId())
                .password(signupReq.getPassword())
                .build();
        Member savedMember = Member.builder()
                .memberId(1L)
                .account(account)
                .name(signupReq.getName())
                .email(signupReq.getEmail())
                .roleType(RoleType.ROLE_MEMBER)
                .accountQR(AccountQR.builder().qrImage(qrImage).build())
                .build();
        given(signupRepository.save(any())).willReturn(savedMember);

        //When
        signupService.signup(signupReq);

        //Then
        then(qrGenerator).should(times(1)).createAccountQRCodeImage();
        then(signupRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("C2-01. 회원가입 실패 - 중복 아이디")
    void 회원가입_실패_중복아이디() {
        // Given
        given(signupRepository.existsByAccountLoginId(signupReq.getLoginId())).willThrow(LoginIdDuplicatedException.class);

        // When, Then
        assertThrows(LoginIdDuplicatedException.class, () -> signupService.signup(signupReq));
    }

    @Test
    @DisplayName("C2-02. 회원가입 실패 - 중복 이메일")
    void 회원가입_실패_중복이메일() {
        // Given
        given(signupRepository.existsByEmail(signupReq.getEmail())).willThrow(EmailDuplicatedException.class);

        // When, Then
        assertThrows(EmailDuplicatedException.class, () -> signupService.signup(signupReq));
    }

    @Test
    @DisplayName("C2-03. 회원가입 실패 - QR 생성 실패")
    void 회원가입_실패_QR생성실패() {
        // Given
        given(signupRepository.existsByAccountLoginId(signupReq.getLoginId())).willReturn(false);
        given(signupRepository.existsByEmail(signupReq.getEmail())).willReturn(false);

        given(qrGenerator.createAccountQRCodeImage()).willThrow(QRNotCreatedException.class);

        // When, Then
        assertThrows(AccountQRNotCreatedException.class, () -> signupService.signup(signupReq));
    }
}
