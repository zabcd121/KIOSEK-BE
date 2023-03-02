package com.cse.cseprojectroommanagementserver.integrationtest.domain.penaltypolicy.api;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyReqDto;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithAdminSecurityFilter;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.PenaltyPolicySetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyReqDto.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminPenaltyPolicyApiControllerIntegrationTest extends BaseIntegrationTestWithAdminSecurityFilter {

    @Autowired
    private PenaltyPolicySetUp penaltyPolicySetUp;

    /**
     * 테스트 케이스
     * M1. 제재 정책 변경 기능
         * M1-C1-01. 제재 정책 변경 성공
     * M2. 현재 제재 정책 조회 기능
         * M2-C1-01. 현재 제재 정책 조회 성공
     */

    @Test
    @DisplayName("M1-C1-01. 제재 정책 변경 기능 성공 ")
    void 제재정책변경_성공() throws Exception {
        // Given
        PenaltyPolicy originPenaltyPolicy = penaltyPolicySetUp.savePenaltyPolicy(3, 2);
        PenaltyPolicyChangeReq newPenaltyPolicyReq = PenaltyPolicyChangeReq.builder()
                .penaltyPolicyId(originPenaltyPolicy.getPenaltyPolicyId())
                .violationCountToImposePenalty(5)
                .numberOfSuspensionDay(5)
                .build();
        // When
        ResultActions resultActions = mvc.perform(
                        put("/api/admins/v1/penalties/policies")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newPenaltyPolicyReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
        assertEquals(originPenaltyPolicy.getAppliedStatus(), AppliedStatus.DEPRECATED);
    }


}