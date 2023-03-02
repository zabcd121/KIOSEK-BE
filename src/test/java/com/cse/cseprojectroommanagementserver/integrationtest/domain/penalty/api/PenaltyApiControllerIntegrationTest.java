package com.cse.cseprojectroommanagementserver.integrationtest.domain.penalty.api;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTest;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationWithSecurityFilter;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.PenaltySetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PenaltyApiControllerIntegrationTest extends BaseIntegrationWithSecurityFilter {

    @Autowired
    private PenaltySetUp penaltySetUp;

    /**
     * C1-01. 개인 제재 내역 조회 성공
     */

    @Test
    @DisplayName("C1-01. 개인 제재 내역 조회 성공")
    void 개인제재내역조회_성공() throws Exception {
        // Given
        Long memberId = member.getMemberId();
        Penalty penalty1 = penaltySetUp.savePenalty(member, LocalDate.now().minusDays(10), LocalDate.now().minusDays(7));
        Penalty penalty2 = penaltySetUp.savePenalty(member, LocalDate.now(), LocalDate.now().plusDays(3));

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/penalties")
                                .header("Authorization", accessToken)
                                .param("memberId", memberId.toString())
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].penaltyId").value(penalty1.getPenaltyId()))
                .andExpect(jsonPath("$.result[1].penaltyId").value(penalty2.getPenaltyId()));
    }
}