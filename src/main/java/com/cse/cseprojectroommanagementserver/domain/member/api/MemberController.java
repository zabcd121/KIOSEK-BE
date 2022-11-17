package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.MemberAuthService;
import com.cse.cseprojectroommanagementserver.domain.member.dto.request.LoginRequest;
import com.cse.cseprojectroommanagementserver.domain.member.dto.request.SignupRequest;
import com.cse.cseprojectroommanagementserver.domain.member.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberAuthService memberAuthService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Validated SignupRequest signupDto) {
        memberAuthService.signup(signupDto);
    }


}