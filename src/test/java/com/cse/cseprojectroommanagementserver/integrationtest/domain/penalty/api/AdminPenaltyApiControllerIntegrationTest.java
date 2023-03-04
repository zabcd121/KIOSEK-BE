package com.cse.cseprojectroommanagementserver.integrationtest.domain.penalty.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithIgnoringURI;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.PenaltySetUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyReqDto.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminPenaltyApiControllerIntegrationTest extends BaseIntegrationTestWithIgnoringURI {

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    private PenaltySetUp penaltySetUp;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String accessToken;

    @BeforeEach
    void setUp() {
        String loginId = RandomStringUtils.random(8, true, true);
        memberSetUp.saveAdmin(loginId, passwordEncoder.encode("admin1!"));
        LoginRes loginRes = authService.login(MemberReqDto.LoginReq.builder().loginId(loginId).password("admin1!").build(), RoleType.ROLE_ADMIN);
        accessToken = loginRes.getTokenInfo().getAccessToken();
    }

    /** 테스트 케이스
     * M1. 사용자 제재
         * M1-C1-01. 사용자 제재 성공
     * M2. 제재 회원 리스트 조회
         * M2-C1-01. 제재 회원 리스트 조회 성공
     */

    @Test
    @DisplayName("M1-C1-01. 사용자 제재 성공 ")
    void 사용자제재_성공() throws Exception {
        // Given
        Member member = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "member1!",
                RandomStringUtils.random(8, false, true) + "kumoh.ac.kr", "김현석");
        PenaltyImpositionReq penaltyImpositionReq = PenaltyImpositionReq.builder()
                .memberId(member.getMemberId())
                .description("자리 청소 불량")
                .startDt(LocalDate.now())
                .endDt(LocalDate.now().plusDays(3))
                .build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/admins/v1/penalties")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(penaltyImpositionReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M2-C1-01. 제재 회원 리스트 조회 성공")
    void 제재회원리스트조회_성공() throws Exception {
        // Given
        Member member = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "member1!",
                RandomStringUtils.random(8, false, true) + "kumoh.ac.kr", "김현석");
        penaltySetUp.savePenalty(member, LocalDate.now().minusDays(10), LocalDate.now().minusDays(7));
        penaltySetUp.savePenalty(member, LocalDate.now(), LocalDate.now().plusDays(3));

        PenaltySearchCondition penaltySearchCondition = PenaltySearchCondition.builder().memberName(member.getName()).loginId(member.getLoginId()).build();

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/penalties")
                                .header("Authorization", accessToken)
                                .param("loginId", penaltySearchCondition.getLoginId())
                                .param("memberName", penaltySearchCondition.getMemberName())
                                .param("pageNumber", "0")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(2)))
                .andExpect(jsonPath("$.result.content[0].member.loginId").value(member.getLoginId()))
                .andExpect(jsonPath("$.result.content[1].member.loginId").value(member.getLoginId()));

    }
}