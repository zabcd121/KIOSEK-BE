package com.cse.cseprojectroommanagementserver.integrationtest.domain.member.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithIgnoringURI;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.TokenDto.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberAuthApiControllerIntegrationTest extends BaseIntegrationTestWithIgnoringURI {

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * M1.로그인
         * M1-C1. 로그인 성공
     * M2.로그아웃
         * M2-C1. 로그아웃 성공
     * M3. Access 토큰 재발급
         * M3-C1. Access 토큰 재발급 성공
         * M3-C2. Access 토큰 재발급 실패 - 로그아웃한 토큰으로는 재발급 받을 수 없음.
     */

    @Test
    @DisplayName("M1-C1. 로그인 성공")
    void 로그인_성공() throws Exception {
        // Given
        Member savedMember = memberSetUp.saveMember("20180335", passwordEncoder.encode("password1!"), "20180335@kumoh.ac.kr", "김현석");
        LoginReq loginReq = new LoginReq("20180335", "password1!");

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

    @Test
    @DisplayName("M2-C1. 로그아웃 성공")
    void 로그아웃_성공() throws Exception {
        // Given
        memberSetUp.saveMember("20180335", passwordEncoder.encode("password1!"), "20180335@kumoh.ac.kr", "김현석");
        LoginRes loginRes = authService.login(new LoginReq("20180335", "password1!"), RoleType.ROLE_MEMBER);

        TokensDto tokensDto = loginRes.getTokenInfo();

        // When
        ResultActions resultActions = mvc.perform(
                        delete("/api/v2/members/logout")
                                .header("Authorization", tokensDto.getAccessToken())
//                                .contentType(APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(tokensDto))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M3-C1. Access 토큰 재발급 성공")
    void 액세스토큰재발급_성공 () throws Exception {
        // Given
        memberSetUp.saveMember("20180335", passwordEncoder.encode("password1!"), "20180335@kumoh.ac.kr", "김현석");
        LoginRes loginRes = authService.login(new LoginReq("20180335", "password1!"), RoleType.ROLE_MEMBER);


        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v2/members/token/reissue")
                                .header("Authorization", loginRes.getTokenInfo().getRefreshToken())
                                .contentType(APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M3-C2. Access 토큰 재발급 실패 - 로그아웃한 토큰으로는 재발급 받을 수 없음.")
    void 액세스토큰재발급_실패 () throws Exception {
        // Given
        memberSetUp.saveMember("20180335", passwordEncoder.encode("password1!"), "20180335@kumoh.ac.kr", "김현석");
        LoginRes loginRes = authService.login(new LoginReq("20180335", "password1!"), RoleType.ROLE_MEMBER);
        TokensDto tokenInfo = loginRes.getTokenInfo();

        authService.logout(jwtTokenProvider.resolveToken(tokenInfo.getAccessToken()));

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v2/members/token/reissue")
                                .header("Authorization", tokenInfo.getRefreshToken())
//                                .contentType(APPLICATION_JSON)
//                                .param("refreshToken", tokenInfo.getRefreshToken())
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isNotFound());
    }
}