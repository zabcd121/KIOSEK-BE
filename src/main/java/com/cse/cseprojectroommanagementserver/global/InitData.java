package com.cse.cseprojectroommanagementserver.global;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Account;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.RoleType;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.NumberOfSuspensionDay;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.PenaltyPolicy;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.domain.model.ViolationCountToImposePenalty;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxCountPerDay;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxHourPerOnce;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationMaxPeriod;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.global.dto.AppliedStatus;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.util.aes256.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        //initService.reservationInit();
        //initService.adminInit();
        //initService.dataInit();
        //initService.penaltyPolicyDataInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final AES256 aes256;

        public void reservationInit() {

//            for (int i=1; i < 12; i++) {
//                for (int d=1; d < 29; d++) {
//                    for (int j=1; j<24; j++) {
//                        for (int k=0; k<59; k++) {
//                            for (int x=0; x<59; x++) {
//                                LocalDateTime startAt = LocalDateTime.of(2023, d, i, j, k, x);
//                                em.persist(
//                                        Reservation.builder()
//                                                .reservationStatus(ReservationStatus.RESERVATION_COMPLETED)
//                                                .startAt(startAt)
//                                                .endAt(LocalDateTime.of(2023, d, i, j, k, x).plusHours(2))
//                                );
//                            }
//                        }
//                    }
//                }
//            }

            for (int i=1; i < 12; i++) {
                for (int d=1; d < 10; d++) {
                        LocalDateTime startAt = LocalDateTime.of(2023, d, i, 0, 0, 0);
                        em.persist(
                                Reservation.builder()
                                        .reservationStatus(ReservationStatus.RESERVATION_COMPLETED)
                                        .startAt(startAt)
                                        .endAt(LocalDateTime.of(2023, d, i, 0, 0, 0).plusHours(2))
                        );
                }
            }
        }

        public void adminInit() {
            Member admin1 = Member.builder().account(Account.builder().loginId(aes256.encrypt("kitadmin1")).password(passwordEncoder.encode("admin17540!")).build()).name("관리자1").email("admin1@kiosek.kr").roleType(RoleType.ROLE_ADMIN).build();
            Member admin2 = Member.builder().account(Account.builder().loginId(aes256.encrypt("kitadmin2")).password(passwordEncoder.encode("admin27540!")).build()).name("관리자2").email("admin2@kiosek.kr").roleType(RoleType.ROLE_ADMIN).build();
            em.persist(admin1);
            em.persist(admin2);

            Member me = Member.builder().account(Account.builder().loginId(aes256.encrypt("20180335")).password(passwordEncoder.encode("love1994@")).build()).name("김현석").email(aes256.encrypt("zabcd121@kumoh.ac.kr")).roleType(RoleType.ROLE_MEMBER).build();
            em.persist(me);
        }

        public void dataInit() throws Exception {
            Member member1 = Member.createMember(Account.builder().loginId(aes256.encrypt("20990001")).password(passwordEncoder.encode("abcd12345!")).build(), "20990001@kumoh.ac.kr", "홍길동", QRImage.builder().content("123").fileOriName("qwe").fileLocalName("sdad").fileUrl("asdad").build());
            Member member2 = Member.createMember(Account.builder().loginId(aes256.encrypt("20990002")).password(passwordEncoder.encode("abcd12345!")).build(), "20990002@kumoh.ac.kr", "소공이", QRImage.builder().content("231").fileOriName("asdf").fileLocalName("gdsq").fileUrl("evasc").build());

            //Member admin = Member.builder().account(Account.builder().loginId("admin").password("admin1!").build()).name("관리자1").build();
//            em.persist(admin);
            em.persist(member1);
            em.persist(member2);
//
//            for (int i=3; i<1000; i++) {
//                String loginId = "2099";
//                if (i>100) {
//                    loginId += "0" + i;
//                } else {
//                    loginId += "00" + i;
//                }
//                em.persist(Member.createMember(
//                        Account.builder()
//                                .loginId(aes256.encrypt(loginId))
//                                .password(passwordEncoder.encode("abcd12345!"))
//                                .build(),
//                        loginId + "@kumoh.ac.kr", "소공이" + i, QRImage.builder()
//                        .content("231").fileOriName("asdf").fileLocalName("gdsq").fileUrl("evasc").build()));
//            }

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
            ProjectTable projectTable13 = ProjectTable.builder().tableName("B7").projectRoom(projectRoom2).build();
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
            em.persist(projectTable13);

            ReservationPolicy reservationPolicy = ReservationPolicy.builder()
                    .reservationMaxHourPerOnce(new ReservationMaxHourPerOnce(4))
                    .reservationMaxCountPerDay(new ReservationMaxCountPerDay(20))
                    .reservationMaxPeriod(new ReservationMaxPeriod(2))
                    .appliedStatus(AppliedStatus.CURRENTLY)
                    .build();
            em.persist(reservationPolicy);
        }

        public void penaltyPolicyDataInit() {
            PenaltyPolicy penaltyPolicy = PenaltyPolicy.createPenaltyPolicy(3, 3);
            em.persist(penaltyPolicy);
        }

        public void projectTableDataInit() {

        }
    }
}
