package com.cse.cseprojectroommanagementserver.unittest.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.member.exception.WrongPasswordException;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.jwt.MemberDetailsService;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {

    @InjectMocks
    AuthService authService;

    @Mock MemberSearchableRepository memberSearchableRepository;
    @Mock JwtTokenProvider jwtTokenProvider;
    @Mock MemberDetailsService memberDetailsService;
    @Mock RedisTemplate redisTemplate;
    @Mock PasswordEncoder passwordEncoder;
    @Mock AES256 aes256;

    LoginReq loginReq;

    /** 테스트 케이스
     * C1. 로그인 성공
         * C1-01. 로그인 성공 - 일반 회원 아이디, 패스워드 모두 일치
         * C1-02. 로그인 성공 - 관리자 아이디, 패스워드 모두 일치
     * C2. 로그인 실패
         * C2-01. 로그인 실패 - 아이디 불일치
         * C2-02. 로그인 실패 - 아이디 일치, 비밀번호 불일치
     */

    @BeforeEach
    void setUp() {
        loginReq = new LoginReq("20180335", "kiosek1234!");
    }

    @Test
    @DisplayName("C1-01. 로그인 성공 - 일반 회원 아이디, 패스워드 모두 일치")
    void 로그인_성공_일반회원_아이디패스워드_모두일치() {
        templateOfLoginSuccessTest(ROLE_MEMBER);
    }

    @Test
    @DisplayName("C1-02. 로그인 성공 - 관리자 아이디, 패스워드 모두 일치")
    void 로그인_성공_관리자_아이디패스워드_모두일치() {
        templateOfLoginSuccessTest(ROLE_ADMIN);
    }

    @Test
    @DisplayName("C2-01. 로그인 실패 - 아이디 불일치 ")
    void 로그인_실패_아이디_불일치() {
        // Given
        given(aes256.encrypt(loginReq.getLoginId())).willReturn("encryptId");
        given(memberSearchableRepository.findByAccountLoginId("encryptId")).willThrow(UsernameNotFoundException.class);

        // When, Then
        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginReq, ROLE_MEMBER));
    }

    @Test
    @DisplayName("C2-02. 로그인 실패 - 아이디 일치, 비밀번호 불일치")
    void 로그인_실패_아이디일치_비밀번호불일치() {
        // Given
        Member savedMember = getMember(ROLE_MEMBER);

        given(aes256.encrypt(loginReq.getLoginId())).willReturn("encryptId");
        given(memberSearchableRepository.findByAccountLoginId("encryptId")).willReturn(Optional.of(savedMember));
        User user = getUserOfSavedMember(savedMember);
        given(memberDetailsService.loadUserByUsername(savedMember.getMemberId().toString())).willReturn(user);
        given(passwordEncoder.matches(loginReq.getPassword(), user.getPassword())).willReturn(false);

        // When, Then
        assertThrows(WrongPasswordException.class, () -> authService.login(loginReq, ROLE_MEMBER));
    }
    
    private void templateOfLoginSuccessTest(RoleType roleType) {
        //Given
        Member savedMember = getMember(roleType);
        AccountQR accountQR = getAccountQR();
        savedMember.changeAccountQR(accountQR);

        given(aes256.encrypt(loginReq.getLoginId())).willReturn("encryptId");
        given(memberSearchableRepository.findByAccountLoginId("encryptId")).willReturn(Optional.of(savedMember));

        User user = getUserOfSavedMember(savedMember);

        given(memberDetailsService.loadUserByUsername(savedMember.getMemberId().toString())).willReturn(user);
        given(passwordEncoder.matches(loginReq.getPassword(), user.getPassword())).willReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        given(jwtTokenProvider.createAccessToken(authentication)).willReturn(getAccessToken());
        given(jwtTokenProvider.createRefreshToken(authentication)).willReturn(getRefreshToken());

        ValueOperations valueOperations = mock(ValueOperations.class);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(jwtTokenProvider.getExpiration(getRefreshToken())).willReturn(5L);
        willDoNothing().given(valueOperations).set("RT:" + authentication.getName(), getRefreshToken(), 5L , TimeUnit.MILLISECONDS);


        // When
        LoginRes loginRes = authService.login(loginReq, roleType);

        // Then
        assertEquals(savedMember.getMemberId(), loginRes.getMemberInfo().getMemberId());
    }

    private Member getMember(RoleType roleType) {
        return Member.builder()
                .memberId(1L)
                .name("김현석")
                .email("encryptEmail")
                .account(Account.builder().loginId("encryptId").password("kiosek1234!").build())
                .roleType(roleType)
                .build();
    }

    private AccountQR getAccountQR() {
        return AccountQR.builder()
                .accountQRId(1L)
                .qrImage(QRImage.builder().fileLocalName("20180335.jpg").fileOriName("20180335.jpg").fileUrl("/src/").content("content").build())
                .build();
    }

    private User getUserOfSavedMember(Member savedMember) {
        return new User(savedMember.getName(), savedMember.getPassword(),
                savedMember.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .collect(Collectors.toList()));
    }

    private String getAccessToken() {
        return "Bearer accessToken";
    }

    private String getRefreshToken() {
        return "Bearer refreshToken";
    }

}