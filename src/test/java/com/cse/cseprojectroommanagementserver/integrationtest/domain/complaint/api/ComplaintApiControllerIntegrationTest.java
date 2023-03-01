package com.cse.cseprojectroommanagementserver.integrationtest.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintReqDto;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTest;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ComplaintSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MockMultipartFIleExampleMaker;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectRoomSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintReqDto.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ComplaintApiControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMultipartFIleExampleMaker mockMultipartFIleExampleMaker;

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
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
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
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());
    }


}