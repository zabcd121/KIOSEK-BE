package com.cse.cseprojectroommanagementserver.integrationtest.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithIgnoringURI;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminAuthApiControllerIntegrationTest extends BaseIntegrationTestWithIgnoringURI {

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * M1.관리자 로그인
         * M1-C01. 관리자 로그인 성공
     */

    @Test
    @DisplayName("M1-C01. 로그인 성공")
    void 관리자로그인_성공() throws Exception {
        // Given
        Member savedMember = memberSetUp.saveAdmin("20180335", passwordEncoder.encode("password1!"));
        LoginReq loginReq = new LoginReq("20180335", "password1!");

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/admins/v1/login")
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