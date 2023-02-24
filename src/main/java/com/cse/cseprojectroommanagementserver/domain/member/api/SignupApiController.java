package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.SignupService;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto;
import com.cse.cseprojectroommanagementserver.domain.member.exception.EmailDuplicatedException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.LoginIdDuplicatedException;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupApiController {
    private final SignupService signupService;

    @GetMapping("/v1/members/signup/check-id")
    public ResponseSuccessNoResult isDuplicatedLoginId(@RequestParam String loginId) {
        if(!signupService.isDuplicatedLoginId(loginId)) {
            return new ResponseSuccessNoResult(LOGIN_ID_NOT_DUPLICATED);
        } else {
            throw new LoginIdDuplicatedException();
        }
    }

    @GetMapping("/v1/members/signup/check-email")
    public ResponseSuccessNoResult isDuplicatedEmail(@RequestParam String email) {
        if(!signupService.isDuplicatedEmail(email)) {
            return new ResponseSuccessNoResult(EMAIL_USABLE);
        } else {
            throw new EmailDuplicatedException();
        }
    }

    @PostMapping("/v1/members/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccessNoResult signup(@RequestBody @Validated SignupRequest signupDto) {
        signupService.signup(signupDto);
        return new ResponseSuccessNoResult(SIGNUP_SUCCESS);
    }
}
