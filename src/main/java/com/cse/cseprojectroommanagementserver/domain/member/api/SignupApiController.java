package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.SignupService;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupApiController {
    private final SignupService signupService;

    @GetMapping("/v1/members/signup/check-id")
    public ResponseSuccessNoResult isDuplicatedLoginId(@RequestParam String loginId) {
        signupService.checkDuplicationLoginId(loginId);
        return new ResponseSuccessNoResult(LOGIN_USABLE);
    }

    @GetMapping("/v1/members/signup/check-email")
    public ResponseSuccessNoResult isDuplicatedEmail(@RequestParam String email) {
        signupService.checkDuplicationEmail(email);
        return new ResponseSuccessNoResult(EMAIL_USABLE);
    }

    @PostMapping("/v1/members/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccessNoResult signup(@RequestBody @Validated SignupReq signupReq) {
        signupService.signup(signupReq);
        return new ResponseSuccessNoResult(SIGNUP_SUCCESS);
    }
}
