package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.domain.repository.MemberSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.member.exception.*;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.jwt.MemberDetailsService;
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

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberSearchableRepository memberSearchRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final RedisTemplate redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, RoleType allowedRoleType) {
        Authentication authentication = authenticateMember(loginRequest.getLoginId(), loginRequest.getPassword(), allowedRoleType);

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        saveRefreshTokenInRedis(refreshToken, authentication);

        Member member = memberSearchRepository.findByAccountLoginId(loginRequest.getLoginId()).orElseGet(null);

        return LoginResponse.builder()
                .memberInfo(getLoginMemberInfoResponse(member))
                .tokenInfo(getTokensDto(accessToken, refreshToken))
                .build();
    }

    @Transactional
    public TokensDto reissueAccessToken(String refreshToken) {
        log.info("refresh: {}", refreshToken);

        String resolvedRefreshToken = resolveToken(refreshToken);

        jwtTokenProvider.validateToken(resolvedRefreshToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolvedRefreshToken);

        String findRedisRefreshToken = (String) redisTemplate.opsForValue().get(RT + authentication.getName());

        if (!resolvedRefreshToken.equals(findRedisRefreshToken)) {
            throw new NotExistsEqualRefreshToken();
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(authentication);
        redisTemplate.opsForValue().set(
                RT + authentication.getName(),
                newRefreshToken,
                jwtTokenProvider.getExpiration(newRefreshToken),
                TimeUnit.MILLISECONDS);


        return TokensDto.builder()
                .accessToken(BEARER + newAccessToken)
                .refreshToken(BEARER + newRefreshToken)
                .build();
    }

    /**
     * logout시에 Redis에 해당 멤버의 refreshToken이 있다면 삭제하고 accessToken을 key로 남은 유효기간동안 Redis에 "logout" value와 함께 저장한다.
     * @param tokensDto
     */
    @Transactional
    public void logout(TokensDto tokensDto) {
        String resolvedAccessToken = resolveToken(tokensDto.getAccessToken());

        jwtTokenProvider.validateToken(resolvedAccessToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolvedAccessToken);

        if(redisTemplate.opsForValue().get(RT + authentication.getName()) != null) {
            redisTemplate.delete(RT + authentication.getName());
        }

        // 남은 유효시간동안만 블랙리스트에 저장하고 그 뒤에는 자동으로 삭제되도록 하면 후에 해커가 탈취한 토큰으로 로그인을 시도하더라도 만료돼서 인증 실패함
        Long remainedExpiration = jwtTokenProvider.getExpiration(resolvedAccessToken);
        redisTemplate.opsForValue()
                .set(resolvedAccessToken, "logout", remainedExpiration, TimeUnit.MILLISECONDS);

    }

    public LoginMemberInfoResponse searchMemberInfo(String resolvedAccessToken) {
        String loginId = jwtTokenProvider.getSubject(resolvedAccessToken);
        Member member = memberSearchRepository.findByAccountLoginId(loginId).orElseThrow(() -> new NotExistsMemberException());

        return LoginMemberInfoResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .roleType(member.getRoleType())
                .build();
    }

    public Member searchMatchedMember(String loginId, String password) {
        Member member = memberSearchRepository.findByAccountLoginId(loginId).orElseThrow(() -> new NotExistsMemberException());
        matchingPassword(password, member.getPassword());

        return member;
    }

    public Member searchMatchedMember(String accountQRContents) {
        return memberSearchRepository.findByAccountQRContents(accountQRContents)
                .orElseThrow(() -> new InvalidAccountQRException());
    }


    private Authentication authenticateMember(String loginId, String password, RoleType allowedRoleType){
        UserDetails userDetails = memberDetailsService.loadUserByUsername(loginId);

        matchingPassword(password, userDetails.getPassword());

        String role = "";
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            role = authority.getAuthority();
            log.info("role = {}", role);
        }

        if(!role.equals(allowedRoleType.toString())) {
            throw new NoAuthorityToLoginException();
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    private void saveRefreshTokenInRedis(String refreshToken, Authentication authentication) {
        redisTemplate.opsForValue()
                .set(RT + authentication.getName(), refreshToken, jwtTokenProvider.getExpiration(refreshToken), TimeUnit.MILLISECONDS);
    }

    private LoginMemberInfoResponse getLoginMemberInfoResponse(Member member) {
        return LoginMemberInfoResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .roleType(member.getRoleType())
                .build();
    }

    private TokensDto getTokensDto(String accessToken, String refreshToken) {
        return TokensDto.builder()
                .accessToken(BEARER + accessToken)
                .refreshToken(BEARER + refreshToken)
                .build();
    }

    private boolean matchingPassword(String requestPassword, String originPassword) {
        if(!passwordEncoder.matches(requestPassword, originPassword)) {
            throw new InvalidPasswordException();
        }
        return true;
    }

    public String resolveToken(String token) {
        log.info(token);
        log.info(token);
        if (token.startsWith(BEARER))
            return token.substring(7);
        throw new TokenNotBearerTypeException();
    }

}
