package com.cse.cseprojectroommanagementserver.integrationtest.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTest;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberAuthApiControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * M1. 로그인
         * M1-C01. 로그인 성공
     * M2. 로그아웃
         * M2-C01. 로그아웃 성공
     */

    @Test
    @DisplayName("M1-C01. 로그인 성공")
    void 로그인_성공() throws Exception {
        // Given
        Member savedMember = memberSetUp.saveMember("20180335", passwordEncoder.encode("password1!"), "20180335@kumoh.ac.kr", "김현석");
        LoginReq loginReq = LoginReq.builder().loginId("20180335").password("password1!").build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/members/login")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.memberInfo.memberId").value(savedMember.getMemberId()));
    }

}