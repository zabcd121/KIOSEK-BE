package com.cse.cseprojectroommanagementserver.integrationtest.domain.projectroom.api;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithIgnoringURI;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectRoomSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProjectRoomApiControllerIntegrationTest extends BaseIntegrationTestWithIgnoringURI {

    @Autowired
    private ProjectRoomSetUp projectRoomSetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    /**
     * C1-01. 모든 프로젝트실과 테이블 리스트 조회 성공
     */

    @Test
    @DisplayName("C1-01. 모든 프로젝트실과 테이블 리스트 조회 성공")
    void 모든프로젝트실과테이블리스트조회_성공() throws Exception {
        // When
        ResultActions resultActions = mvc.perform(get("/api/v1/rooms")
                        .characterEncoding("UTF-8")
                        .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].projectTableList", hasSize(6)))
                .andExpect(jsonPath("$.result[1].projectTableList", hasSize(6)));
    }
}