package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberRepository;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.*;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.jwt.MemberDetailsService;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.TokenDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberSearchableRepository memberSearchableRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final RedisTemplate redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AES256 aes256;

    @Timed("kiosek.member")
    @Transactional
    public LoginRes login(LoginReq loginReq, RoleType allowedRoleType) {
        Member member = searchMemberByLoginId(loginReq.getLoginId());

        Authentication authentication = authenticateMember(member, loginReq.getPassword(), allowedRoleType);

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        saveRefreshToken(refreshToken, authentication);

        return LoginRes.of(
                TokensDto.of(BEARER + accessToken, BEARER + refreshToken),
                LoginMemberInfoRes.of(member));
    }

    @Transactional
    public TokensDto reissueToken(String resolvedRefreshToken) {
        try {
            jwtTokenProvider.validateToken(resolvedRefreshToken);
        } catch (TokenException ex) {
            throw new ExpiredException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(resolvedRefreshToken);

        String findRedisRefreshToken = (String) redisTemplate.opsForValue().get(RT + authentication.getName());

        if (!resolvedRefreshToken.equals(findRedisRefreshToken)) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_REFRESH_TOKEN_IN_STORE);
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);

        redisTemplate.opsForValue().set(
                RT + authentication.getName(),
                newRefreshToken,
                jwtTokenProvider.getExpiration(newRefreshToken),
                TimeUnit.MILLISECONDS);


        return TokensDto.of(BEARER + newAccessToken, BEARER + newRefreshToken);
    }

    /**
     * logout시에 Redis에 해당 멤버의 refreshToken이 있다면 삭제하고 accessToken을 key로 남은 유효기간동안 Redis에 "logout" value와 함께 저장한다.
     */
    @Transactional
    public void logout(String resolvedAccessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(resolvedAccessToken);

        if(redisTemplate.opsForValue().get(RT + authentication.getName()) != null) {
            redisTemplate.delete(RT + authentication.getName());
        }

        // 남은 유효시간동안만 블랙리스트에 저장하고 그 뒤에는 자동으로 삭제되도록 하면 후에 해커가 탈취한 토큰으로 로그인을 시도하더라도 만료돼서 인증 실패함
        Long remainedExpiration = jwtTokenProvider.getExpiration(resolvedAccessToken);
        redisTemplate.opsForValue()
                .set(resolvedAccessToken, "logout", remainedExpiration, TimeUnit.MILLISECONDS);
    }

    public LoginMemberInfoRes searchMemberInfoByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER));

        return LoginMemberInfoRes.of(member);
    }

    public Member searchMemberByAccountQR(String accountQRContents) {
        return memberSearchableRepository.findByAccountQRContents(accountQRContents)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ACCOUNT_QR));
    }

    public String searchEmailByLoginId(String loginId) {
        String decryptedEmail = aes256.decrypt(searchMemberByLoginId(loginId).getEmail());

        return decryptedEmail;
    }

    private Member searchMemberByLoginId(String loginId) {
        return memberSearchableRepository.findByAccountLoginId(aes256.encrypt(loginId))
                .orElseThrow(() -> new NotFoundException(ErrorCode.ID_NOT_FOUND));
    }

    private Authentication authenticateMember(Member member, String password, RoleType allowedRoleType) {
        UserDetails userDetails = memberDetailsService.loadUserByUsername(member.getMemberId().toString());

        validatePassword(password, userDetails.getPassword());

        String role = "";
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            role = authority.getAuthority();
        }

        if(!role.equals(allowedRoleType.toString())) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    private boolean validatePassword(String requestPassword, String originPassword) {
        if(!passwordEncoder.matches(requestPassword, originPassword)) {
            throw new IncorrectException(ErrorCode.INCORRECT_PASSWORD);
        }
        return true;
    }

    private void saveRefreshToken(String refreshToken, Authentication authentication) {
        redisTemplate.opsForValue()
                .set(RT + authentication.getName(), refreshToken, jwtTokenProvider.getExpiration(refreshToken), TimeUnit.MILLISECONDS);
    }
}
