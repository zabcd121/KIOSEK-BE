package com.cse.cseprojectroommanagementserver.integrationtest.domain.penalty.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberReqDto;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTest;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyReqDto.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminPenaltyApiControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MemberSetUp memberSetUp;

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
}