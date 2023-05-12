package com.cse.cseprojectroommanagementserver.integrationtest.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationqr.domain.model.ReservationQR;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilter;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationPolicySetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationSetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservationApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilter {

    @Autowired
    private ReservationPolicySetUp reservationPolicySetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    @Autowired
    private ReservationSetUp reservationSetUp;

    /**
     * M1. 웹 예약 기능
         * M1-C1-01. 웹 예약 기능 성공
     * M2. 현장 예약 기능
         * M2-C1-01. 현장 예약 기능 성공
     * M3. 해당 프로젝트실과 시간대에 예약 내역 조회 기능
         * M3-C1-01. 해당 프로젝트실과 시간대에 예약 내역 조회 기능 성공
     * M4. 현재 예약된 테이블인지 확인 기능 - Q&A 스피커에서 사람이 감지되었을 때 현재 이 테이블이 예약되어 사용중인 테이블인지 검사하는 API
         * M4-C1-01. 현재 예약된 테이블인지 확인 기능 성공 - Q&A 스피커에서 사람이 감지되었을 때 현재 이 테이블이 예약되어 사용중인 테이블인지 검사하는 API
     * M5. 현재 나의 예약 내역 조회 기능
         * M5-C1-01. 현재 나의 예약 내역 조회 기능 성공
     * M6. 과거 나의 예약 내역 조회 기능
         * M6-C1-01. 과거 나의 예약 내역 조회 기능 성공
     * M7. 예약 취소 기능
         * M7-C1-01. 예약 취소 기능 성공
     * M8. 예약 체크인 기능
         * M8-C1-01. 예약 체크인 기능 성공
     */

    @Disabled
    @Test
    @DisplayName("M1-C1-01. 웹 예약 기능 성공")
    void 웹예약_성공() throws Exception {
        // Given
        ReservationPolicy reservationPolicy = reservationPolicySetUp.findReservationPolicy();
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * 7 + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour());
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        ReserveReq reserveReq = ReserveReq.builder()
                .projectTableId(projectTable.getTableId())
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v2/reservations")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reserveReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M2-C1-01. 현장 예약 기능 성공 - By 회원 QR 인증")
    void 현장예약_By회원qr인증_성공() throws Exception {
        // Given
        ReservationPolicy reservationPolicy = reservationPolicySetUp.findReservationPolicy();
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * 7 + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour());
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        OnsiteReservationByQRReq onsiteReservationByQRReq = OnsiteReservationByQRReq.builder()
                .accountQRContents(member.getAccountQR().getQrImage().getContent())
                .projectTableId(projectTable.getTableId())
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/reservations/onsite/qr")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(onsiteReservationByQRReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M3-C1-01. 해당 프로젝트실과 시간대에 예약 내역 및 테이블 비활성화 내역 조회 기능 성공")
    void 예약내역조회_프로젝트실별시간대별_성공() throws Exception {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime searchCondStartAt = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth()+1, 8, 0);
        LocalDateTime searchCondEndAt = searchCondStartAt.plusDays(1);
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        LocalDateTime reservationStartAt = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth()+1, 9, 0);
        reservationSetUp.saveReservation(member, projectTable, reservationStartAt, reservationStartAt.plusHours(2));

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v1/reservations")
                                .header("Authorization", accessToken)
                                .param("projectRoomId", projectTable.getProjectRoom().getProjectRoomId().toString())
                                .param("firstAt", searchCondStartAt.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT)))
                                .param("lastAt", searchCondEndAt.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT)))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.reservedList", hasSize(1)));
    }

    @Disabled
    @Test
    @DisplayName("M4-C1-01. 현재 예약된 테이블인지 확인 기능 성공 - Q&A 스피커에서 사람이 감지되었을 때 현재 이 테이블이 예약되어 사용중인 테이블인지 검사하는 API")
    void 현재예약된테이블인지확인_QA스피커_성공() throws Exception {
        // Given
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        LocalDateTime reservationStartAt = LocalDateTime.now().minusHours(1);
        reservationSetUp.saveReservationWithStatus(IN_USE, member, projectTable, reservationStartAt, reservationStartAt.plusHours(2));

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/sensor/v1/reservations/check")
                                .param("tableName", projectTable.getTableName())
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("M5-C1-01. 현재 나의 예약 내역 조회 기능 성공")
    void 현재나의예약내역조회_성공() throws Exception {
        // Given
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, projectTable, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2));
        reservationSetUp.saveReservationWithStatus(RETURNED, member, projectTable, LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(3).plusHours(2));

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v2/reservations/current")
                                .header("Authorization", accessToken)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(1)));
    }

    @Test
    @DisplayName("M6-C1-01. 과거 나의 예약 내역 조회 기능 성공")
    void 과거나의예약내역조회_성공() throws Exception {
        // Given
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, projectTable, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2));
        reservationSetUp.saveReservationWithStatus(RETURNED, member, projectTable, LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(3).plusHours(2));

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/v2/reservations/past")
                                .header("Authorization", accessToken)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(1)));
    }

    @Test
    @DisplayName("M7-C1-01. 예약 취소 기능 성공")
    void 예약취소_성공() throws Exception {
        // Given
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        Reservation reservation = reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, projectTable, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2));

        // When
        ResultActions resultActions = mvc.perform(
                        delete("/api/v1/reservations/{id}", reservation.getReservationId())
                                .header("Authorization", accessToken)
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
        assertEquals(reservation.getReservationStatus(), CANCELED);
    }

    @Test
    @DisplayName("M8-C1-01. 예약 체크인 기능 성공")
    void 예약체크인_성공() throws Exception {
        // Given
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        Reservation reservation = reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, projectTable, LocalDateTime.now().plusMinutes(9), LocalDateTime.now().plusHours(2));
        QRImage qrImage = reservation.getReservationQR().getQrImage();
        QRAuthReq qrAuthReq = new QRAuthReq(qrImage.getContent());

        // When
        ResultActions resultActions = mvc.perform(
                        post("/api/v1/reservations/auth")
                                .header("Authorization", accessToken)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(qrAuthReq))
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk());
        assertEquals(reservation.getReservationStatus(), IN_USE);
    }

    @AfterTransaction
    void setAfter() {
        reservationSetUp.deleteAll();
    }

}