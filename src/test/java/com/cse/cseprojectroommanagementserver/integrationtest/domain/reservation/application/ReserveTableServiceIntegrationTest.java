package com.cse.cseprojectroommanagementserver.integrationtest.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReserveTableWithLockService;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.MemberSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ProjectTableSetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationPolicySetUp;
import com.cse.cseprojectroommanagementserver.integrationtest.setup.ReservationSetUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;

@SpringBootTest
@Transactional
class ReserveTableServiceIntegrationTest {

    @Autowired
    private ReservationPolicySetUp reservationPolicySetUp;

    @Autowired
    private ProjectTableSetUp projectTableSetUp;

    @Autowired
    private ReservationSetUp reservationSetUp;

    @Autowired
    private MemberSetUp memberSetUp;

    @Autowired
    private ReserveTableWithLockService reserveTableService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회의공간 예약 동시성 테스트 - Named Lock 적용")
    void 회의공간예약동시성테스트() throws InterruptedException {
        // given
        Member member1 = memberSetUp.findMember("20990001");
        ReservationPolicy reservationPolicy = reservationPolicySetUp.findReservationPolicy();
        LocalDateTime reservationStartAt = LocalDateTime.of(LocalDateTime.now().toLocalDate().plusDays(reservationPolicy.getReservationMaxPeriod().getMaxPeriod() * 7 + 1), LocalTime.of(4, 0));
        LocalDateTime reservationEndAt = reservationStartAt.plusHours(reservationPolicy.getReservationMaxHourPerOnce().getMaxHour());
        ProjectTable projectTable = projectTableSetUp.findProjectTableByTableName("A1");

        ReserveReq reserveReq1 = ReserveReq.builder()
                .projectTableId(projectTable.getTableId())
                .startAt(reservationStartAt)
                .endAt(reservationEndAt)
                .build();


        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i=0; i<threadCount; i++) {
            executorService.execute(() -> {
                try {
                    reserveTableService.reserve(member1.getMemberId(), reserveReq1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        List<Reservation> findReservationList = reservationSetUp.findAll();
        System.out.println(findReservationList.size());
        Assertions.assertEquals(1, findReservationList.size());

    }
}