package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberDto;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.jwt.MemberDetailsService;
import com.cse.cseprojectroommanagementserver.global.util.AccountQRNotCreatedException;
import com.cse.cseprojectroommanagementserver.global.util.QRGenerator;
import com.cse.cseprojectroommanagementserver.global.util.QRNotCreatedException;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final QRGenerator qrGenerator;
    private final RedisTemplate redisTemplate;

    @Transactional
    public void signup(MemberDto.SignupRequest signupDto) {
        if (!isDuplicatedLoginId(signupDto.getLoginId()) && !isDuplicatedEmail(signupDto.getEmail())) {
            try {
                QRImage accountQRCodeImage = qrGenerator.createAccountQRCodeImage(signupDto.getLoginId());
                Account account = Account.builder().loginId(signupDto.getLoginId()).password(passwordEncoder.encode(signupDto.getPassword())).build();

                Member signupMember = Member.createMember(account, signupDto.getEmail(), signupDto.getName(), accountQRCodeImage);

                memberRepository.save(signupMember);
            } catch (WriterException | IOException | QRNotCreatedException e) {
                throw new AccountQRNotCreatedException(ACCOUNT_QR_CREATE_FAIL.getMessage());
            }
        }
    }

    public boolean isDuplicatedLoginId(String loginId) {
        return memberRepository.existsByAccountLoginId(loginId);
    }

    public boolean isDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public MemberDto.LoginResponse login(MemberDto.LoginRequest loginRequest) {
        String loginIdEntered = loginRequest.getLoginId();
        String passwordEntered = loginRequest.getPassword();

        UserDetails userDetails = memberDetailsService.loadUserByUsername(loginIdEntered);

        if (!passwordEncoder.matches(passwordEntered, userDetails.getPassword())) {
            throw new BadCredentialsException(userDetails.getUsername() + " " + PASSWORD_INVALID.getMessage());
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), refreshToken, jwtTokenProvider.getExpiration(refreshToken), TimeUnit.MILLISECONDS);

        MemberDto.TokensDto tokensDto = MemberDto.TokensDto.builder()
                .accessToken(BEARER + jwtTokenProvider.createAccessToken(authentication))
                .refreshToken(BEARER + refreshToken)
                .build();

        Member member = memberRepository.findByAccountLoginId(loginIdEntered).orElseGet(null);


        MemberDto.LoginMemberInfoResponse loginMemberInfoResponse = MemberDto.LoginMemberInfoResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .roleType(member.getRoleType())
                .build();

        return MemberDto.LoginResponse.builder()
                .memberInfo(loginMemberInfoResponse)
                .tokenInfo(tokensDto)
                .build();

    }

    @Transactional
    public MemberDto.TokensDto reissueAccessToken(String refreshToken) {
        log.info("refresh: {}", refreshToken);

        String resolvedRefreshToken = resolveToken(refreshToken);

        jwtTokenProvider.validateToken(resolvedRefreshToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolvedRefreshToken);

        String findRedisRefreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());

        if (!resolvedRefreshToken.equals(findRedisRefreshToken)) {
            throw new RuntimeException("not equals refresh token");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);
        redisTemplate.opsForValue().set(
                "RT:" + authentication.getName(),
                newRefreshToken,
                jwtTokenProvider.getExpiration(newRefreshToken),
                TimeUnit.MILLISECONDS);


        return MemberDto.TokensDto.builder()
                .accessToken("Bearer " + newAccessToken)
                .refreshToken("Bearer " + newRefreshToken)
                .build();
    }

    /**
     * logout시에 Redis에 해당 멤버의 refreshToken이 있다면 삭제하고 accessToken을 key로 남은 유효기간동안 Redis에 "logout" value와 함께 저장한다.
     * @param tokensDto
     */
    @Transactional
    public void logout(MemberDto.TokensDto tokensDto) {
        String resolvedAccessToken = resolveToken(tokensDto.getAccessToken());

        jwtTokenProvider.validateToken(resolvedAccessToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolvedAccessToken);

        if(redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 남은 유효시간동안만 블랙리스트에 저장하고 그 뒤에는 자동으로 삭제되도록 하면 후에 해커가 탈취한 토큰으로 로그인을 시도하더라도 만료돼서 인증 실패함
        Long remainedExpiration = jwtTokenProvider.getExpiration(resolvedAccessToken);
        redisTemplate.opsForValue()
                .set(resolvedAccessToken, "logout", remainedExpiration, TimeUnit.MILLISECONDS);

    }


    private String resolveToken(String token) {
        log.info(token);
        if (token.startsWith("Bearer "))
            return token.substring(7);
        throw new RuntimeException("not valid refresh token !!");
    }


}
