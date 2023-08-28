package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.application.EmailService;
import com.cse.cseprojectroommanagementserver.domain.member.application.MemberSearchService;
import com.cse.cseprojectroommanagementserver.domain.member.application.MemberUpdateService;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponseNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberApiController {
    private final String PASSWORD_REISSUE_EMAIL_TITLE = "KIOSEK 비밀번호 재발급 인증 번호";

    private final MemberSearchService memberSearchService;
    private final MemberUpdateService memberUpdateService;
    private final AuthService authService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/v2/members")
    public SuccessResponse<MemberComplexInfoRes> getMemberProfile(HttpServletRequest request) {
        MemberComplexInfoRes memberComplexInfo = memberSearchService.searchMyPageInfo(
                jwtTokenProvider.getSubjectWith(request));
        return new SuccessResponse<>(MYPAGE_SEARCH_SUCCESS, memberComplexInfo);
    }

    @PatchMapping("/v1/members/password")
    public SuccessResponseNoResult changePassword(@RequestBody @Validated PasswordChangeReq passwordChangeReq,
                                                  HttpServletRequest request) {
        memberUpdateService.changePassword(jwtTokenProvider.getSubjectWith(request), passwordChangeReq);
        return new SuccessResponseNoResult(CHANGE_PASSWORD_SUCCESS);
    }

    @GetMapping("/v1/members/password")
    public SuccessResponseNoResult sendPasswordReissueAuthCodeToEmail(@RequestParam("loginId") String loginId) throws MessagingException {
        String email = authService.searchEmailByLoginId(loginId);
        emailService.sendPasswordRecreateAuthCode(email, PASSWORD_REISSUE_EMAIL_TITLE);

        return new SuccessResponseNoResult(AUTH_CODE_SEND_SUCCESS);
    }

    @PostMapping("/v1/members/password-authcode")
    public SuccessResponseNoResult verifyPasswordReissueAuthCode(@RequestBody @Validated PasswordReissueCodeAuthReq passwordReissueCodeAuthReq) {
        String email = authService.searchEmailByLoginId(passwordReissueCodeAuthReq.getLoginId());
        emailService.verifyEmailAuthCode(email, passwordReissueCodeAuthReq.getCode());

        return new SuccessResponseNoResult(AUTH_CODE_VERIFY_SUCCESS);
    }

    @PatchMapping("/v1/members/password/reissuance")
    public SuccessResponseNoResult reissuePassword(@RequestBody @Validated PasswordReissueReq passwordReissueReq) {
        memberUpdateService.changePasswordWithNoLogin(passwordReissueReq);

        return new SuccessResponseNoResult(CHANGE_PASSWORD_SUCCESS);
    }
}