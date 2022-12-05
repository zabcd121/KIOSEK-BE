package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.application.MemberComplexInfoSearchService;
import com.cse.cseprojectroommanagementserver.domain.member.application.SignupService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.exception.EmailDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.LoginIdDuplicatedException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final AuthService authService;
    private final SignupService signupService;
    private final MemberComplexInfoSearchService memberComplexInfoSearchService;

    @GetMapping("/duplicated-loginid")
    public ResponseSuccessNoResult isDuplicatedLoginId(@RequestParam String loginId) {
        if(!signupService.isDuplicatedLoginId(loginId)) {
            return new ResponseSuccessNoResult(LOGIN_ID_NOT_DUPLICATED);
        } else {
            throw new LoginIdDuplicatedException();
        }
    }

    @GetMapping("/duplicated-email")
    public ResponseSuccessNoResult isDuplicatedEmail(@RequestParam String email) {
        if(!signupService.isDuplicatedEmail(email)) {
            return new ResponseSuccessNoResult(EMAIL_USABLE);
        } else {
            throw new EmailDuplicatedException();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccessNoResult signup(@RequestBody @Validated SignupRequest signupDto) {
        signupService.signup(signupDto);
        return new ResponseSuccessNoResult(SIGNUP_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseSuccess<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest, RoleType.ROLE_MEMBER);
        return new ResponseSuccess(LOGIN_SUCCESS, loginResponse);
    }

    @DeleteMapping("/logout")
    public ResponseSuccessNoResult logout(@RequestBody TokensDto tokensDto) {
        authService.logout(tokensDto);
        return new ResponseSuccessNoResult(LOGOUT_SUCCESS);
    }

    /**
     * Access token이 만료되었을 경우 재발급 요청 api
     * @param refreshToken : Refresh token을 입력받는다.
     * @return TokenResponseDto : Access token과 Refresh token 모두 재발급해준다.
     */
    @PostMapping("/token/reissue")
    public ResponseSuccess<TokensDto> reissueAccessToken(@RequestParam String refreshToken){
        TokensDto tokensDto = authService.reissueAccessToken(refreshToken);
        return new ResponseSuccess(TOKEN_REISSUED, tokensDto);
    }

    @GetMapping("/reissue")
    public ResponseSuccess<LoginMemberInfoResponse> refreshMemberInfo(HttpServletRequest request) {
        String resolvedToken = authService.resolveToken(request.getHeader(AUTHORIZATION_HEADER));
        LoginMemberInfoResponse memberInfo = authService.searchMemberInfo(resolvedToken);
        return new ResponseSuccess<>(MEMBER_INFO_REISSUED, memberInfo);
    }

    @GetMapping("/{id}")
    public ResponseSuccess<MemberComplexInfoResponse> getMemberComplexInfo(@PathVariable("id") Long memberId) {
        MemberComplexInfoResponse memberComplexInfo = memberComplexInfoSearchService.searchMemberComplexInfo(memberId);
        return new ResponseSuccess<>(MEMBER_MY_PAGE_INFO_SEARCH_SUCCESS, memberComplexInfo);
    }



}