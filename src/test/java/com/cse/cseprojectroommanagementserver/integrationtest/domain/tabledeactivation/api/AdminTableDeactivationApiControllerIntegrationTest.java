package com.cse.cseprojectroommanagementserver.integrationtest.domain.tabledeactivation.api;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilterForAdmin;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectRoomSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.TableDeactivationSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminTableDeactivationApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilterForAdmin {

    @Autowired
    private TableDeactivationSetUp tableDeactivationSetUp;

    @Autowired
    private ProjectRoomSetUp projectRoomSetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    /**
     * M1. 예약 비활성화 기능
         * M1-C1-01. 예약 비활성화 기능 성공
     * M2. 예약 비활성화 내역 조회 기능
         * M2-C1-01. 예약 비활성화 내역 조회 기능 성공
     */

    @Test
    @DisplayName("예약 비활성화 기능 성공")
    void 예약비활성화_성공() throws Exception {
        // Given
        ProjectRoom projectRoom = projectRoomSetUp.findProjectRoomByRoomName("D330");

        ProjectTable table1 = projectTableSetUp.findProjectTableByTableName("A1");
        ProjectTable table2 = projectTableSetUp.findProjectTableByTableName("A2");
        List projectTableIdList = new ArrayList<>();
        projectTableIdList.add(table1.getTableId());
        projectTableIdList.add(table2.getTableId());

        TableDeactivationInfoReq tableDeactivationInfoReq = TableDeactivationInfoReq.builder()
                .reason("웹프로그래밍 수업")
                .startAt(LocalDateTime.now().plusDays(3))
                .endAt(LocalDateTime.now().plusDays(3).plusHours(2))
                .build();

        TableDeactivationReq tableDeactivationReq = TableDeactivationReq.builder()
                .projectRoomId(projectRoom.getProjectRoomId())
                .projectTableIdList(projectTableIdList)
                .tableDeactivationInfoReq(tableDeactivationInfoReq)
                .build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/admins/v1/table-deactivations")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(tableDeactivationReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M2-C1-01. 예약 비활성화 내역 조회 기능 성공 ")
    void 예약비활성화내역조회_성공() throws Exception {
        // Given
        ProjectTable table1 = projectTableSetUp.findProjectTableByTableName("A1");
        ProjectTable table2 = projectTableSetUp.findProjectTableByTableName("A2");

        TableDeactivationInfo tableDeactivationInfo = TableDeactivationInfo.builder()
                .reason("웹프로그래밍 수업")
                .startAt(LocalDateTime.now().plusDays(3))
                .endAt(LocalDateTime.now().plusDays(3).plusHours(2))
                .build();

        tableDeactivationSetUp.saveTableDeactivation(table1, tableDeactivationInfo);
        tableDeactivationSetUp.saveTableDeactivation(table2, tableDeactivationInfo);

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/table-deactivations")
                                .header("Authorization", accessToken)
                                .param("page", "0")
                                .param("size", "2")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(2)));
    }
}