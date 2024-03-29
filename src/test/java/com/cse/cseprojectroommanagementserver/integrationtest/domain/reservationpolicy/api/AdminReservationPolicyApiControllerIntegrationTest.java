package com.cse.cseprojectroommanagementserver.integrationtest.domain.reservationpolicy.api;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilterForAdmin;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationPolicySetUp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminReservationPolicyApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilterForAdmin {

    @Autowired
    private ReservationPolicySetUp reservationPolicySetUp;

    /**
     * M1. 예약 정책 변경 기능
         * C1. 예약 정책 변경 기능 성공
             * M1-C1-01. 예약 정책 변경 기능 성공
     * M2. 예약 정책 조회 기능
         * C1. 예약 정책 조회 기능 성공
             * M2-C1-01. 예약 정책 조회 기능 성공
     */

    @Test
    @DisplayName("M1-C1-01. 예약 정책 변경 기능 성공")
    void 예약정책변경_성공() throws Exception {
        // Given
        ReservationPolicy originReservationPolicy = reservationPolicySetUp.findReservationPolicy();

        ReservationPolicyChangeReq reservationPolicyChangeReq = ReservationPolicyChangeReq.builder()
                .reservationPolicyId(originReservationPolicy.getReservationPolicyId())
                .reservationMaxHourPerOnce(5)
                .reservationMaxCountPerDay(2)
                .reservationMaxPeriod(3)
                .build();

        // When
        ResultActions resultActions = mvc.perform(
                        put("/api/admins/v1/reservations/policies")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reservationPolicyChangeReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
        assertEquals(DEPRECATED, originReservationPolicy.getAppliedStatus());
    }

    @Test
    @DisplayName("M2-C1-01. 예약 정책 조회 기능 성공")
    void 예약정책조회_성공() throws Exception {
        // Given
        ReservationPolicy reservationPolicy = reservationPolicySetUp.findReservationPolicy();

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/reservations/policies")
                                .header("Authorization", accessToken)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.reservationPolicyId").value(reservationPolicy.getReservationPolicyId()));
    }

}
