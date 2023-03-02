package com.cse.cseprojectroommanagementserver.integrationtest.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTest;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SignupApiControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MemberSetUp memberSetUp;

    /**
     * M1. 아이디 중복 체크 기능
         * M1-C1-01. 아이디 중복 체크 기능 성공 - 중복 아님
         * M1-C2-01. 아이디 중복 체크 기능 실패 - 중복 에러
     * M2. 이메일 중복 체크 기능
         * M2-C1-01. 이메일 중복 체크 기능 성공 - 중복 아님
         * M1-C2-01. 이메일 중복 체크 기능 실패 - 중복 에러
     * M3. 회원가입 기능
         * M3-C1-01. 회원가입 성공
         * M3-C2-01. 회원가입 실패 - ID 중복
         * M3-C2-01. 회원가입 실패 - Email 중복
     */

    @Test
    @DisplayName("M1-C1-01. 아이디 중복 체크 기능 성공 - 중복 아님")
    void 아이디중복체크_성공_중복X() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/members/signup/check-id")
                                .param("loginId", loginIdReq)
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M1-C2-01. 아이디 중복 체크 기능 성공 - 중복 에러")
    void 아이디중복체크_성공_중복O() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        memberSetUp.saveMember(loginIdReq, "password1!", "20180335@kumoh.ac.kr", "김현석");

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/members/signup/check-id")
                                .param("loginId", loginIdReq)
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("M2-C1-01. 이메일 중복 체크 기능 성공 - 중복 아님")
    void 이메일중복체크_성공_중복X() throws Exception {
        // Given
        String emailReq = RandomStringUtils.random(8, false, true);
        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/members/signup/check-email")
                                .param("email", emailReq)
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M1-C2-01. 이메일 중복 체크 기능 실패 - 중복 에러")
    void 이메일중복체크_성공_중복O() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        String emailReq = RandomStringUtils.random(8, false, true) + "@kumoh.ac.kr";
        memberSetUp.saveMember(loginIdReq, "password1!", emailReq, "김현석");

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/members/signup/check-email")
                                .param("email", emailReq)
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("M3-C1-01. 회원가입 성공")
    void 회원가입_성공() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        SignupReq signupReq = SignupReq.builder().loginId(loginIdReq).password("password1!").email("email@kumoh.ac.kr").name("김현석").build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/members/signup")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("M3-C2-01. 회원가입 실패 - ID 중복")
    void 회원가입_실패_ID중복() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        memberSetUp.saveMember(loginIdReq, "password1!", "20180335@kumoh.ac.kr", "김현석");
        SignupReq signupReq = SignupReq.builder().loginId(loginIdReq).password("password1!").email("email@kumoh.ac.kr").name("김현석").build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/members/signup")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("M3-C2-01. 회원가입 실패 - Email 중복")
    void 회원가입_실패_email중복() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        String emailReq = RandomStringUtils.random(8, false, true) + "@kumoh.ac.kr";
        memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", emailReq, "김현석");
        SignupReq signupReq = SignupReq.builder().loginId(loginIdReq).password("password1!").email(emailReq).name("김현석").build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/members/signup")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isConflict());
    }

}