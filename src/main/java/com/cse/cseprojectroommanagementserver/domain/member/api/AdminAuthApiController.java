package com.cse.cseprojectroommanagementserver.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.LOGIN_SUCCESS;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminAuthApiController {
    private final AuthService authService;

    @PostMapping("/v1/login")
    public ResponseSuccess<LoginReq> login(@RequestBody @Validated LoginReq loginReq) {
        LoginRes loginResponse = authService.login(loginReq, RoleType.ROLE_ADMIN);
        return new ResponseSuccess(LOGIN_SUCCESS, loginResponse);
    }
}