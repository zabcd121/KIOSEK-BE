package com.cse.cseprojectroommanagementserver.integrationtest.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.EmailService;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithIgnoringURI;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.ResultActions;

import java.util.concurrent.TimeUnit;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.EV;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SignupApiControllerIntegrationTest extends BaseIntegrationTestWithIgnoringURI {

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * M1. 아이디 중복 체크 기능
         * M1-C1-01. 아이디 중복 체크 기능 성공 - 중복 아님
         * M1-C2-01. 아이디 중복 체크 기능 실패 - 중복 에러
     * M2. 이메일 중복 체크 기능
         * M2-C1-01. 이메일 중복 체크 기능 성공 - 중복 아님
         * M2-C2-01. 이메일 중복 체크 기능 실패 - 중복 에러
     * M3. 인증코드 전송 기능
         * M3-C1-01. 인증코드 전송 기능 성공
     * M4. 인증코드 검증 기능
         * M4-C1-01. 인증코드 검증 기능 성공
     * M5. 회원가입 기능
         * M5-C1-01. 회원가입 성공
         * M5-C2-01. 회원가입 실패 - ID 중복
         * M5-C2-01. 회원가입 실패 - Email 중복
         * M5-C2-02. 회원가입 실패 - Email 인증코드 미인증 상태
         * M5-C2-03. 회원가입 실패 - kumoh Email 형식이 아님
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
    @DisplayName("M2-C2-01. 이메일 중복 체크 기능 실패 - 중복 에러")
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
    @DisplayName("M3-C1-01. 인증코드 전송 기능 성공 ")
    void 인증코드전송_성공() throws Exception {
        // Given
        String toEmail = "20180335@kumoh.ac.kr";

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/members/signup/authcode")
                                .param("email", toEmail)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M5-C1-01. 회원가입 성공")
    void 회원가입_성공() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        SignupReq signupReq = SignupReq.builder().loginId(loginIdReq).password("password1!").email("email@kumoh.ac.kr").name("김현석").build();
        redisTemplate.opsForValue()
                .set(EV + signupReq.getEmail(), "completed", 180000L, TimeUnit.MILLISECONDS); // 이메일 인증코드 인증 완료상태

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
    @DisplayName("M5-C2-01. 회원가입 실패 - ID 중복")
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
    @DisplayName("M5-C2-01. 회원가입 실패 - Email 중복")
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

    @Test
    @DisplayName("M5-C2-02. 회원가입 실패 - Email 인증코드 미인증 상태")
    void 회원가입_실패_인증코드미인증상태() throws Exception {
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
        resultActions.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("M5-C2-03. 회원가입 실패 - kumoh Email 형식이 아님")
    void 회원가입_실패_kumoh메일이아닌경우() throws Exception {
        // Given
        String loginIdReq = RandomStringUtils.random(8, false, true);
        SignupReq signupReq = SignupReq.builder().loginId(loginIdReq).password("password1!").email("email@naver.com").name("김현석").build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/members/signup")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(signupReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isBadRequest());
    }

    @AfterEach
    void afterEach() {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

}