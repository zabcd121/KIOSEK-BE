package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberRequestDto;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.LOGIN_SUCCESS;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminApiController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseSuccess<MemberRequestDto.LoginRequest> login(@RequestBody @Validated MemberRequestDto.LoginRequest loginRequest) {
        MemberResponseDto.LoginResponse loginResponse = authService.login(loginRequest, RoleType.ROLE_ADMIN);
        return new ResponseSuccess(LOGIN_SUCCESS, loginResponse);
    }
}
