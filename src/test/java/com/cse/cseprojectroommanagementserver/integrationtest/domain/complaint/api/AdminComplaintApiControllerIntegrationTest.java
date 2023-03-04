package com.cse.cseprojectroommanagementserver.integrationtest.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilterForAdmin;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ComplaintSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectRoomSetUp;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminComplaintApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilterForAdmin {

    @Autowired
    private ComplaintSetUp complaintSetUp;

    @Autowired
    private ProjectRoomSetUp projectRoomSetUp;

    /**
     * C1-01. 민원 내역 조회 성공
     */

    @Test
    @DisplayName("C1-01. 민원 내역 조회 성공")
    void 민원내역조회_성공() throws Exception {
        // Given
        complaintSetUp.saveComplaint(projectRoomSetUp.findProjectRoomByRoomName("D330"),
                "쓰레기통이 너무 지저분합니다.", "쓰레기통이 꽉차서 너무 바닥까지 너무 더럽습니다.",
                Image.builder().fileOriName("test").fileLocalName("test").fileUrl("/test").build());

        complaintSetUp.saveComplaint(projectRoomSetUp.findProjectRoomByRoomName("DB134"),
                "B1자리가 청결불량", "B1테이블 이전 사용자가 너무 더럽게 사용했습니다.",
                Image.builder().fileOriName("test").fileLocalName("test").fileUrl("/test").build());

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/complaints")
                                .header("Authorization", accessToken)
                                .param("page", "0")
                                .param("size", "2")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(2)));

    }
}