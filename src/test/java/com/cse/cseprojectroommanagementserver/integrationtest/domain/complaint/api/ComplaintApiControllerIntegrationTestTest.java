package com.cse.cseprojectroommanagementserver.integrationtest.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilter;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MockMultipartFileExampleMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ComplaintApiControllerIntegrationTestTest extends BaseIntegrationTestWithSecurityFilter {

    @Autowired
    private MockMultipartFileExampleMaker mockMultipartFIleExampleMaker;

    /**
     * 테스트 케이스
     * C1. 민원 성공
         * C1-01. 민원 성공 - 이미지 첨부 O
         * C1-02. 민원 성공 - 이미지 첨부 X
     * C2. 민원 실패
     */

    @Test
    @DisplayName("C1-01. 민원 성공 - 이미지 첨부 O")
    void 민원_성공_이미지첨부O() throws Exception {
        // Given, When
        ResultActions resultActions = mvc.perform(
                multipart("/api/v1/complaints")
                        .file(mockMultipartFIleExampleMaker.getMockMultipartFile())
                        .param("projectRoomId", "4")
                        .param("subject", "subject2")
                        .param("content", "content")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("C1-02. 민원 성공 - 이미지 첨부 X")
    void 민원_성공_이미지첨부X() throws Exception {
        // Given, When
        ResultActions resultActions = mvc.perform(
                        multipart("/api/v1/complaints")
                                .param("projectRoomId", "4")
                                .param("subject", "subject2")
                                .param("content", "content")
                                .header("Authorization", accessToken)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());
    }
}