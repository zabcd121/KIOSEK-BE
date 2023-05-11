package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.EmailService;
import com.cse.cseprojectroommanagementserver.domain.member.application.SignupService;
import com.cse.cseprojectroommanagementserver.global.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.ResConditionCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignupApiController {
    private final SignupService signupService;
    private final EmailService emailService;

    @GetMapping("/v1/members/signup/check-id")
    public ResponseSuccessNoResult isDuplicatedLoginId(@RequestParam String loginId) {
        signupService.checkDuplicationLoginId(loginId);
        return new ResponseSuccessNoResult(LOGIN_ID_DUPLICATION_CHECK_SUCCESS_USABLE);
    }

    @GetMapping("/v1/members/signup/check-email")
    public ResponseSuccessNoResult isDuplicatedEmail(@RequestParam @Email(message = "유효하지 않은 이메일 형식입니다.", regexp = "^[a-zA-Z0-9._%+-]+@kumoh.ac.kr$") @Size(max = 50) String email) {
        signupService.checkDuplicationEmail(email);
        return new ResponseSuccessNoResult(EMAIL_DUPLICATION_CHECK_SUCCESS_USABLE);
    }

    @GetMapping("/v1/members/signup/authcode")
    public ResponseSuccessNoResult sendAuthCodeToEmail(@RequestParam String email) throws MessagingException {
        emailService.sendEmail(email);

        return new ResponseSuccessNoResult(AUTH_CODE_SEND_SUCCESS);
    }

    @PostMapping("/v1/members/signup/authcode")
    public ResponseSuccessNoResult verifyAuthCodeToEmail(@RequestBody @Validated EmailAuthCodeVerifyReq emailAuthCodeReq) {
        emailService.verifyEmailAuthCode(emailAuthCodeReq.getEmail(), emailAuthCodeReq.getCode());

        return new ResponseSuccessNoResult(AUTH_CODE_VERIFY_SUCCESS);
    }

    @PostMapping("/v1/members/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccessNoResult signup(@RequestBody @Validated SignupReq signupReq) {
        signupService.signup(signupReq);
        return new ResponseSuccessNoResult(SIGNUP_SUCCESS);
    }
}
