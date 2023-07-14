package com.cse.cseprojectroommanagementserver.integrationtest.common;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BaseIntegrationTestWithSecurityFilterForAdmin {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    protected String accessToken;

    protected Member admin;

    @BeforeEach
    void setUp() {
        String loginId = RandomStringUtils.random(8, true, true);
        admin = memberSetUp.saveAdmin(loginId, passwordEncoder.encode("admin1!"), "admin1@example.com", "홍길동");
        MemberResDto.LoginRes loginRes = authService.login(new LoginReq(loginId, "admin1!"), RoleType.ROLE_ADMIN);
        accessToken = loginRes.getTokenInfo().getAccessToken();
    }
}