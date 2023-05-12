package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponseNoResult;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.TokenDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberAuthApiController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/v1/members/login")
    public SuccessResponse<LoginRes> login(@RequestBody @Validated LoginReq loginReq) {
        LoginRes loginResponse = authService.login(loginReq, ROLE_MEMBER);
        return new SuccessResponse(LOGIN_SUCCESS, loginResponse);
    }

    @DeleteMapping("/v2/members/logout")
    public SuccessResponseNoResult logout(HttpServletRequest request) {
        String resolvedToken = jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER));
        authService.logout(resolvedToken);
        return new SuccessResponseNoResult(LOGOUT_SUCCESS);
    }

    /**
     * Access token이 만료되었을 경우 재발급 요청 api
     * @header - Authorization에 : Access Token 대신 Refresh token을 넣어서 준다.
     * @return TokenResponseDto : Access token과 Refresh token 모두 재발급해준다.
     */
    @PostMapping("/v2/members/token/reissue")
    public SuccessResponse<TokensDto> reissueToken(HttpServletRequest request){
        TokensDto tokensDto = authService.reissueToken(
                jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER)));
        return new SuccessResponse(TOKEN_REISSUE_SUCCESS, tokensDto);
    }

    @GetMapping("/v1/members/reissue")
    public SuccessResponse<LoginMemberInfoRes> refreshMemberInfo(HttpServletRequest request) {
        String resolvedToken = jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER));
        LoginMemberInfoRes memberInfo = authService.searchMemberInfo(Long.parseLong(jwtTokenProvider.getSubject(resolvedToken)));
        return new SuccessResponse<>(MEMBER_INFO_REISSUE_SUCCESS, memberInfo);
    }
}