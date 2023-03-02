package com.cse.cseprojectroommanagementserver.integrationtest.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTest;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberApiControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MemberSetUp memberSetUp;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * C1. 회원 개인정보 조회 성공
     */

    @Test
    @DisplayName("C1. 회원 개인정보 조회 성공 ")
    void 회원개인정보조회_성공() throws Exception {
        // Given
        Member savedMember = memberSetUp.saveMember("20180335", passwordEncoder.encode("password1!"), "20180335@kumoh.ac.kr", "김현석");
        LoginRes loginRes = authService.login(LoginReq.builder().loginId("20180335").password("password1!").build(), RoleType.ROLE_MEMBER);

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/members/{id}", savedMember.getMemberId())
                                .header("Authorization", loginRes.getTokenInfo().getAccessToken())
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.qrImage.imageName").value(savedMember.getAccount().getLoginId()));
    }
}