package com.cse.cseprojectroommanagementserver.integrationtest.domain.tablereturn.api;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilter;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MockMultipartFileExampleMaker;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationSetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TableReturnApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilter {

    @Autowired
    private ReservationSetUp reservationSetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    @Autowired
    private MockMultipartFileExampleMaker mockMultipartFileExampleMaker;

    /**
     * C1-01. 테이블 반납 기능 성공
     */

    @Test
    @DisplayName("C1-01. 테이블 반납 기능 성공")
    void 테이블반납_성공() throws Exception {
        // Given

        Reservation reservation = reservationSetUp.saveReservationWithStatus(IN_USE, member, projectTableSetUp.findProjectTableByTableName("A1"),
                LocalDateTime.now().minusHours(2), LocalDateTime.now().plusMinutes(5));

        // When
        ResultActions resultActions = mvc.perform(
                        multipart("/api/v1/returns")
                                .file(mockMultipartFileExampleMaker.getMockMultipartFile())
                                .param("reservationId", reservation.getReservationId().toString())
                                .header("Authorization", accessToken)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk());

        Assertions.assertEquals(RETURNED, reservation.getReservationStatus());
    }

}