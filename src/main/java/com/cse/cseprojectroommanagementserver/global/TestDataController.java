package com.cse.cseprojectroommanagementserver.global;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Means;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/dummy")
@RequiredArgsConstructor
@Transactional
public class TestDataController {

    private final EntityManager em;

    @PostMapping
    public void saveDummy() {
        for (int i=1; i < 12; i++) {
            for (int d=1; d < 29; d++) {
                for (int j=1; j<24; j++) {
                    for (int k=0; k<50; k++) {
                        LocalDateTime startAt = LocalDateTime.of(2023, i, d, j, k);
                        em.persist(
                                Reservation.builder()
                                        .reservationStatus(ReservationStatus.RESERVATION_COMPLETED)
                                        .startAt(startAt)
                                        .endAt(LocalDateTime.of(2023, i, d, j, k).plusHours(2))
                                        .means(Means.WEB)
                                        .member(em.find(Member.class, (long) (i+d+j+k)))
                                        .projectTable(em.find(ProjectTable.class, (long) ((k)%13 + 1)))
                                        .build()
                        );
                    }
                }
            }
            em.flush();
        }
    }
}
