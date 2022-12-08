package com.cse.cseprojectroommanagementserver.global;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.ViolationCountToImposePenalty;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxCountPerDay;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxHourPerOnce;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxPeriod;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        //initService.dataInit();
             //initService.penaltyPolicyDataInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dataInit() {
            Member member1 = Member.createMember(Account.builder().loginId("20180335").password(passwordEncoder.encode("abcd123!")).build(), "20180001@kumoh.ac.kr", "홍길동", QRImage.builder().content("123").fileOriName("qwe").fileLocalName("sdad").fileUrl("asdad").build());
            Member member2 = Member.createMember(Account.builder().loginId("20").password(passwordEncoder.encode("abcd123!")).build(), "20180002@kumoh.ac.kr", "홍길", QRImage.builder().content("231").fileOriName("asdf").fileLocalName("gdsq").fileUrl("evasc").build());
            Member member3 = Member.createMember(Account.builder().loginId("abcd1").password(passwordEncoder.encode("abcd123!")).build(), "20180003@kumoh.ac.kr", "홍동", QRImage.builder().content("321").fileOriName("fda").fileLocalName("gcqs").fileUrl("ceqfg").build());

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            ProjectRoom projectRoom1 = ProjectRoom.builder().buildingName("디지털관").roomName("D330").priority(1).build();
            ProjectRoom projectRoom2 = ProjectRoom.builder().buildingName("디지털관").roomName("DB134").priority(2).build();
            em.persist(projectRoom1);
            em.persist(projectRoom2);

            ProjectTable projectTable1 = ProjectTable.builder().tableName("A1").projectRoom(projectRoom1).build();
            ProjectTable projectTable2 = ProjectTable.builder().tableName("A2").projectRoom(projectRoom1).build();
            ProjectTable projectTable3 = ProjectTable.builder().tableName("A3").projectRoom(projectRoom1).build();
            ProjectTable projectTable4 = ProjectTable.builder().tableName("A4").projectRoom(projectRoom1).build();
            ProjectTable projectTable5 = ProjectTable.builder().tableName("A5").projectRoom(projectRoom1).build();
            ProjectTable projectTable6 = ProjectTable.builder().tableName("A6").projectRoom(projectRoom1).build();

            ProjectTable projectTable7 = ProjectTable.builder().tableName("B1").projectRoom(projectRoom2).build();
            ProjectTable projectTable8 = ProjectTable.builder().tableName("B2").projectRoom(projectRoom2).build();
            ProjectTable projectTable9 = ProjectTable.builder().tableName("B3").projectRoom(projectRoom2).build();
            ProjectTable projectTable10 = ProjectTable.builder().tableName("B4").projectRoom(projectRoom2).build();
            ProjectTable projectTable11 = ProjectTable.builder().tableName("B5").projectRoom(projectRoom2).build();
            ProjectTable projectTable12 = ProjectTable.builder().tableName("B6").projectRoom(projectRoom2).build();
            em.persist(projectTable1);
            em.persist(projectTable2);
            em.persist(projectTable3);
            em.persist(projectTable4);
            em.persist(projectTable5);
            em.persist(projectTable6);
            em.persist(projectTable7);
            em.persist(projectTable8);
            em.persist(projectTable9);
            em.persist(projectTable10);
            em.persist(projectTable11);
            em.persist(projectTable12);

            ReservationPolicy reservationPolicy = ReservationPolicy.builder()
                    .reservationMaxHourPerOnce(ReservationMaxHourPerOnce.builder().maxHourPerOnce(4).build())
                    .reservationMaxCountPerDay(ReservationMaxCountPerDay.builder().maxCountPerDay(1).build())
                    .reservationMaxPeriod(ReservationMaxPeriod.builder().maxPeriod(2).build())
                    .appliedStatus(AppliedStatus.CURRENT)
                    .build();
            em.persist(reservationPolicy);
        }

        public void penaltyPolicyDataInit() {
            PenaltyPolicy penaltyPolicy = PenaltyPolicy.builder()
                    .violationCountToImposePenalty(ViolationCountToImposePenalty.builder()
                            .countOfViolationsToImposePenalty(3)
                            .build())
                    .numberOfSuspensionDay(2)
                    .appliedStatus(AppliedStatus.CURRENT)
                    .build();
            em.persist(penaltyPolicy);
        }

        public void projectTableDataInit() {

        }
    }
}
