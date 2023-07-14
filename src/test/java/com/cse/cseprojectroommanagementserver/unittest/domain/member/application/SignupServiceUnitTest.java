package com.cse.cseprojectroommanagementserver.unittest.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.application.SignupService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.SignupRepository;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.error.exception.DuplicationException;
import com.cse.cseprojectroommanagementserver.global.error.exception.FileSystemException;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import com.cse.cseprojectroommanagementserver.global.util.qrgenerator.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.qrgenerator.QRNotCreatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SignupServiceUnitTest {
    @InjectMocks
    SignupService signupService;

    @Mock SignupRepository signupRepository;
    @Mock QRGenerator qrGenerator;
    @Spy PasswordEncoder passwordEncoder;
    @Mock RedisTemplate redisTemplate;
    @Mock AES256 aes256;

    SignupReq signupReq;

    @BeforeEach
    void setUp() {
        signupReq = SignupReq.builder()
                .loginId("20180335")
                .password("example1234!")
                .email("20180335@kumoh.ac.kr")
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
    void 회원가입_성공() {
        //Given
        given(aes256.encrypt(signupReq.getLoginId())).willReturn("encryptId");
        given(signupRepository.existsByAccountLoginId("encryptId")).willReturn(false);

        given(aes256.encrypt(signupReq.getEmail())).willReturn("encryptEmail");
        given(signupRepository.existsByEmail("encryptEmail")).willReturn(false);
        ValueOperations valueOperations = mock(ValueOperations.class);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue().get(EV + signupReq.getEmail())).willReturn("completed");
        given(redisTemplate.delete(EV + signupReq.getEmail())).willReturn(true);

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
        given(aes256.encrypt(signupReq.getLoginId())).willReturn("encryptId");
        given(signupRepository.existsByAccountLoginId("encryptId")).willThrow(DuplicationException.class);

        // When, Then
        assertThrows(DuplicationException.class, () -> signupService.signup(signupReq));
    }

    @Test
    @DisplayName("C2-02. 회원가입 실패 - 중복 이메일")
    void 회원가입_실패_중복이메일() {
        // Given
        given(aes256.encrypt(signupReq.getLoginId())).willReturn("encryptId");
        given(signupRepository.existsByAccountLoginId("encryptId")).willReturn(false);

        given(aes256.encrypt(signupReq.getEmail())).willReturn("encryptEmail");
        given(signupRepository.existsByEmail("encryptEmail")).willThrow(DuplicationException.class);

        // When, Then
        assertThrows(DuplicationException.class, () -> signupService.signup(signupReq));
    }

    @Test
    @DisplayName("C2-03. 회원가입 실패 - QR 생성 실패")
    void 회원가입_실패_QR생성실패() {
        // Given
        given(aes256.encrypt(signupReq.getLoginId())).willReturn("encryptId");
        given(signupRepository.existsByAccountLoginId("encryptId")).willReturn(false);

        given(aes256.encrypt(signupReq.getEmail())).willReturn("encryptEmail");
        given(signupRepository.existsByEmail("encryptEmail")).willReturn(false);
        ValueOperations valueOperations = mock(ValueOperations.class);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(redisTemplate.opsForValue().get(EV + signupReq.getEmail())).willReturn("completed");
        given(redisTemplate.delete(EV + signupReq.getEmail())).willReturn(true);

        given(qrGenerator.createAccountQRCodeImage()).willThrow(QRNotCreatedException.class);

        // When, Then
        assertThrows(QRNotCreatedException.class, () -> signupService.signup(signupReq));
    }
}
