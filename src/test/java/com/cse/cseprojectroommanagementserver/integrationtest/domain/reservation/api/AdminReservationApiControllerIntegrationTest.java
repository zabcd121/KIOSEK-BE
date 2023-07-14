package com.cse.cseprojectroommanagementserver.integrationtest.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.integrationtest.common.BaseIntegrationTestWithSecurityFilterForAdmin;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectRoomSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationSetUp;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminReservationApiControllerIntegrationTest extends BaseIntegrationTestWithSecurityFilterForAdmin {

    @Autowired
    private ReservationSetUp reservationSetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    @Autowired
    private ProjectRoomSetUp projectRoomSetUp;

    @Autowired
    private MemberSetUp memberSetUp;

    /**
     * C1. 예약 내역 조회 성공
         * C1-01. 예약 내역 조회 성공 - 시작일, 마지막일 조건 사용
         * C1-02. 예약 내역 조회 성공 - 회원 이름 조건 사용
         * C1-03. 예약 내역 조회 성공 - 로그인 ID 조건 사용
         * C1-04. 예약 내역 조회 성공 - 예약 상태 조건 사용
         * C1-05. 예약 내역 조회 성공 - 프로젝트실 이름 조건 사용
         * C1-05. 예약 내역 조회 성공 - 조건 없음
     */

    @Test
    @DisplayName("C1-01. 예약 내역 조회 성공 - 시작일, 마지막일 조건 사용")
    void 예약내역조회_성공_시작일마지막일조건() throws Exception {
        // Given
        ProjectRoom projectRoom = projectRoomSetUp.findProjectRoomByRoomName("D330");
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A3");
        Member member = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "김현석");
        reservationSetUp.saveReservation(member, projectTable, LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusHours(2));
        reservationSetUp.saveReservation(member, projectTable, LocalDateTime.now().minusHours(3), LocalDateTime.now());

        ReservationSearchCondition condition = ReservationSearchCondition.builder().startDt(LocalDate.now().minusDays(4)).endDt(LocalDate.now()).build();
        // When
        ResultActions resultActions = mvc.perform(
                get("/api/admins/v1/reservations")
                        .header("Authorization", accessToken)
                        .param("startDt", condition.getStartDt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                        .param("endDt", condition.getEndDt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                        .param("pageNumber", "0")
                        .characterEncoding("UTF-8")
                        .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(2)));
    }

    @Test
    @DisplayName("C1-02. 예약 내역 조회 성공 - 회원 이름 조건 사용")
    void 예약내역조회_성공_회원이름조건() throws Exception {
        // Given
        ProjectRoom projectRoom = projectRoomSetUp.findProjectRoomByRoomName("D330");
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A3");
        Member member1 = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "김현석");
        Member member2 = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password2!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "홍길");
        reservationSetUp.saveReservation(member1, projectTable, LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusHours(2));
        reservationSetUp.saveReservation(member2, projectTable, LocalDateTime.now().minusHours(3), LocalDateTime.now());

        ReservationSearchCondition condition = ReservationSearchCondition.builder().memberName(member1.getName()).build();
        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/reservations")
                                .header("Authorization", accessToken)
                                .param("memberName", condition.getMemberName())
                                .param("pageNumber", "0")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(1)));
    }

    @Test
    @DisplayName("C1-02. 예약 내역 조회 성공 - 회원 이름 조건 사용")
    void 예약내역조회_성공_로그인ID조건() throws Exception {
        // Given
        ProjectRoom projectRoom = projectRoomSetUp.findProjectRoomByRoomName("D330");
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A3");
        Member member1 = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "김현석");
        Member member2 = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password2!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "홍길");
        reservationSetUp.saveReservation(member1, projectTable, LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusHours(2));
        reservationSetUp.saveReservation(member2, projectTable, LocalDateTime.now().minusHours(3), LocalDateTime.now());

        ReservationSearchCondition condition = ReservationSearchCondition.builder().loginId(member1.getLoginId()).build();
        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/reservations")
                                .header("Authorization", accessToken)
                                .param("loginId", condition.getLoginId())
                                .param("pageNumber", "0")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(1)));
    }

    @Test
    @DisplayName("C1-04. 예약 내역 조회 성공 - 예약 상태 조건 사용")
    void 예약내역조회_성공_예약상태조건() throws Exception {
        // Given
        ProjectRoom projectRoom = projectRoomSetUp.findProjectRoomByRoomName("D330");
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A3");
        Member member = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "김현석");
        Reservation reservation1 = reservationSetUp.saveReservationWithStatus(ReservationStatus.IN_USE, member, projectTable, LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusHours(2));

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/reservations")
                                .header("Authorization", accessToken)
                                .param("reservationStatus", reservation1.getReservationStatus().getStatus())
                                .param("pageNumber", "0")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(1)));
    }

    @Test
    @DisplayName("C1-05. 예약 내역 조회 성공 - 프로젝트실 이름 조건 사용")
    void 예약내역조회_성공_프로젝트실이름조건() throws Exception {
        // Given
        ProjectRoom projectRoom1 = projectRoomSetUp.findProjectRoomByRoomName("D330");
        ProjectRoom projectRoom2 = projectRoomSetUp.findProjectRoomByRoomName("DB134");
        ProjectTable projectTable1 = projectTableSetUp.findProjectTableByTableName("A1");
        ProjectTable projectTable2 = projectTableSetUp.findProjectTableByTableName( "B3");
        Member member = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "김현석");
        Reservation reservation1 = reservationSetUp.saveReservationWithStatus(ReservationStatus.IN_USE, member, projectTable1, LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusHours(2));
        Reservation reservation2 = reservationSetUp.saveReservationWithStatus(ReservationStatus.CANCELED, member, projectTable2, LocalDateTime.now().minusHours(3), LocalDateTime.now());

        ReservationSearchCondition condition = ReservationSearchCondition.builder().startDt(LocalDate.now().minusDays(4)).endDt(LocalDate.now()).build();
        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/reservations")
                                .header("Authorization", accessToken)
                                .param("roomName", reservation1.getProjectTable().getProjectRoom().getRoomName())
                                .param("pageNumber", "0")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(1)));
    }

    @Test
    @DisplayName("C1-06. 예약 내역 조회 성공 - 조건 없음")
    void 예약내역조회_성공_조건없음() throws Exception {
        // Given
        ProjectRoom projectRoom = projectRoomSetUp.findProjectRoomByRoomName("D330");
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A3");
        Member member = memberSetUp.saveMember(RandomStringUtils.random(8, false, true), "password1!", RandomStringUtils.random(8, true, true) + "@kumoh.ac.kr", "김현석");
        reservationSetUp.saveReservation(member, projectTable, LocalDateTime.now().minusDays(4), LocalDateTime.now().minusDays(4).plusHours(2));
        reservationSetUp.saveReservation(member, projectTable, LocalDateTime.now().minusHours(3), LocalDateTime.now());

        // When
        ResultActions resultActions = mvc.perform(
                        get("/api/admins/v1/reservations")
                                .header("Authorization", accessToken)
                                .param("pageNumber", "0")
                                .characterEncoding("UTF-8")
                                .accept(APPLICATION_JSON))
                .andDo(print());

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content", hasSize(2)));
    }
}