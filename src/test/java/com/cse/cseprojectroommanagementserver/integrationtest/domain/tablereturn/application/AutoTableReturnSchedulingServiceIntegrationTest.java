package com.cse.cseprojectroommanagementserver.integrationtest.domain.tablereturn.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.repository.PenaltySearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.application.AutoTableReturnSchedulingService;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.PenaltyPolicySetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationSetUp;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class AutoTableReturnSchedulingServiceIntegrationTest{

    @Autowired
    AutoTableReturnSchedulingService autoTableReturnSchedulingService;

    @Autowired
    ReservationSetUp reservationSetUp;

    @Autowired
    MemberSetUp memberSetUp;

    @Autowired
    PenaltyPolicySetUp penaltyPolicySetUp;

    @Autowired
    PenaltySearchableRepository penaltySearchableRepository;

    @Test
    @Disabled
    @DisplayName("예약 자동 취소 스케줄러 기능 성공")
    void 예약자동취소_성공() {
        // Given
        Member member = memberSetUp.saveMember("20180335", "password1!!", "email@kumoh.ac.kr", "홍길동");
        Reservation reservation1 = reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, null, LocalDateTime.now().minusMinutes(21), LocalDateTime.now().plusHours(1));
        Reservation reservation2 = reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, null, LocalDateTime.now().minusMinutes(21), LocalDateTime.now().plusHours(1));
        Reservation reservation3 = reservationSetUp.saveReservationWithStatus(RESERVATION_COMPLETED, member, null, LocalDateTime.now().minusMinutes(21), LocalDateTime.now().plusHours(1));
        penaltyPolicySetUp.savePenaltyPolicy(3, 3);

        // When
        autoTableReturnSchedulingService.autoCancelUnUsedReservation();

        // Then
        List<Penalty> penaltyList = penaltySearchableRepository.findAllByMemberId(member.getMemberId()).get();
        Assertions.assertEquals(1, penaltyList.size());
    }
}