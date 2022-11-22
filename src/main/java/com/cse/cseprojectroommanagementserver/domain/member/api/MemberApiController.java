package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.MemberAuthService;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberDto;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberAuthService memberAuthService;

    @GetMapping("{loginId}/duplicated-loginid")
    public ResponseSuccessNoResult isDuplicatedLoginId(@PathVariable String loginId) {
        boolean isDuplicated = memberAuthService.isDuplicatedLoginId(loginId);

        if(isDuplicated) {
            return new ResponseSuccessNoResult(LOGIN_ID_DUPLICATED.getCode(), LOGIN_ID_DUPLICATED.getMessage());
        }
        return new ResponseSuccessNoResult(LOGIN_ID_NOT_DUPLICATED.getCode(), LOGIN_ID_NOT_DUPLICATED.getMessage());
    }

    @GetMapping("/{email}/duplicated-email")
    public ResponseSuccessNoResult isDuplicatedEmail(@PathVariable String email) {
        boolean isDuplicated = memberAuthService.isDuplicatedEmail(email);

        if(isDuplicated) {
            return new ResponseSuccessNoResult(EMAIL_DUPLICATED.getCode(), EMAIL_DUPLICATED.getMessage());
        }
        return new ResponseSuccessNoResult(EMAIL_NOT_DUPLICATED.getCode(), EMAIL_NOT_DUPLICATED.getMessage());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccessNoResult signup(@RequestBody @Validated MemberDto.SignupRequest signupDto) {
        memberAuthService.signup(signupDto);
        return new ResponseSuccessNoResult(SIGNUP_SUCCESS.getCode(), SIGNUP_SUCCESS.getMessage());
    }

    @PostMapping("/login")
    public ResponseSuccess login(@RequestBody @Validated MemberDto.LoginRequest loginRequest) {
        MemberDto.LoginResponse loginResponse = memberAuthService.login(loginRequest);
        return new ResponseSuccess(LOGIN_SUCCESS.getCode(), LOGIN_SUCCESS.getMessage(), loginResponse);
    }

    @DeleteMapping("/logout")
    public ResponseSuccessNoResult logout(@RequestBody MemberDto.TokensDto tokensDto) {
        memberAuthService.logout(tokensDto);
        return new ResponseSuccessNoResult(LOGOUT_SUCCESS.getCode(), LOGOUT_SUCCESS.getMessage());
    }

    /**
     * Access token이 만료되었을 경우 재발급 요청 api
     * @param refreshToken : Refresh token을 입력받는다.
     * @return TokenResponseDto : Access token과 Refresh token 모두 재발급해준다.
     */
    @PostMapping("/token/reissue")
    public MemberDto.TokensDto reissueAccessToken(@RequestParam String refreshToken){
        return memberAuthService.reissueAccessToken(refreshToken);
    }



}