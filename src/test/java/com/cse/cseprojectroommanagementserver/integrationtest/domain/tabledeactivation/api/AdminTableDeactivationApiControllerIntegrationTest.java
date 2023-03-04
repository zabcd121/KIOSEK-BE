package com.cse.cseprojectroommanagementserver.integrationtest.domain.tabledeactivation.api;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilterForAdmin;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectRoomSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.AppliedStatus.DEPRECATED;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminTableDeactivationApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilterForAdmin {

    @Autowired
    private ProjectRoomSetUp projectRoomSetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    /**
     * C1. 예약 비활성화 기능 성공
     * C1-01. 예약 비활성화 기능 성공
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
}