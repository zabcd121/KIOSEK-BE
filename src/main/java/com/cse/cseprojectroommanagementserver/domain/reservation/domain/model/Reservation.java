package com.cse.cseprojectroommanagementserver.domain.reservation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.Desk;
import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DESK_ID")
    private Desk desk;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private LocalDateTime checkInTime;

}
