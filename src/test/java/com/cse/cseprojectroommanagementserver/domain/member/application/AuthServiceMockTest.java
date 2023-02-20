package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.AccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto;
import com.cse.cseprojectroommanagementserver.domain.member.exception.InvalidPasswordException;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.jwt.MemberDetailsService;
import org.junit.jupiter.api.Assertions;
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
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceMockTest {

    @InjectMocks
    AuthService authService;

    @Mock
    MemberSearchableRepository memberSearchRepository;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    MemberDetailsService memberDetailsService;

    @Mock
    RedisTemplate redisTemplate;

    @Mock
    PasswordEncoder passwordEncoder;

    Member savedMember;
    String accessToken;
    String refreshToken;

    LoginRequest loginRequest;
    User user;

    @BeforeEach
    void setUp() {
        savedMember = Member.builder()
                .memberId(1L)
                .name("김현석")
                .email("20180335@kumoh.ac.kr")
                .account(Account.builder().loginId("20180335").password("kiosek1234!").build())
                .roleType(ROLE_MEMBER)
                .build();

        AccountQR accountQR = AccountQR.builder().accountQRId(1L)
                .qrImage(QRImage.builder().fileLocalName("20180335.jpg").fileOriName("20180335.jpg").fileUrl("/src/").content("ljashmqwnqwe").build())
                .build();

        savedMember.changeAccountQR(accountQR);

        accessToken = "Bearer accessToken";
        refreshToken = "Bearer refreshToken";

        loginRequest = LoginRequest.builder().loginId("20180335").password("kiosek1234!").build();
        user = new User(savedMember.getName(), savedMember.getPassword(),
                savedMember.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority))
                        .collect(Collectors.toList()));
    }


    @Test
    @DisplayName("로그인 성공 - 아이디, 패스워드 모두 일치")
    void 로그인_성공() {
        // Given
        given(memberDetailsService.loadUserByUsername(loginRequest.getLoginId())).willReturn(user);
        given(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).willReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        given(jwtTokenProvider.createAccessToken(authentication)).willReturn(accessToken);
        given(jwtTokenProvider.createRefreshToken(authentication)).willReturn(refreshToken);

        ValueOperations valueOperations = mock(ValueOperations.class);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(jwtTokenProvider.getExpiration(refreshToken)).willReturn(5L);
        willDoNothing().given(valueOperations).set("RT:" + authentication.getName(), refreshToken, 5L , TimeUnit.MILLISECONDS);
        given(memberSearchRepository.findByAccountLoginId(loginRequest.getLoginId())).willReturn(Optional.of(savedMember));

        // When
        LoginResponse loginResponse = authService.login(loginRequest, ROLE_MEMBER);

        // Then
        assertEquals(savedMember.getMemberId(), loginResponse.getMemberInfo().getMemberId());
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 불일치")
    void 로그인_실패_아이디_일치X() {
        // Given
        given(memberDetailsService.loadUserByUsername(loginRequest.getLoginId())).willThrow(UsernameNotFoundException.class);

        // When, Then
        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginRequest,ROLE_MEMBER));
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 일치, 비밀번호 불일치")
    void 로그인_실패_비밀번호_일치X() {
        // Given
        given(memberDetailsService.loadUserByUsername(loginRequest.getLoginId())).willReturn(user);
        given(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).willReturn(false);

        // When, Then
        assertThrows(InvalidPasswordException.class, () -> authService.login(loginRequest, ROLE_MEMBER));
    }

}