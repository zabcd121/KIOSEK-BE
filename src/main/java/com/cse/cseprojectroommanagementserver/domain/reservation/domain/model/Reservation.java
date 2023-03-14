package com.cse.cseprojectroommanagementserver.domain.reservation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.UnableToCheckInStatusException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.UnableToCheckInTimeException;
import com.cse.cseprojectroommanagementserver.domain.reservation.exception.UnableToCancelReservationException;
import com.cse.cseprojectroommanagementserver.domain.reservationqr.domain.model.ReservationQR;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Means.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;
import static com.cse.cseprojectroommanagementserver.global.util.ReservationFixedPolicy.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_table_id")
    private ProjectTable projectTable;

    @OneToOne(mappedBy = "targetReservation", fetch = FetchType.LAZY)
    private TableReturn tableReturn;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_qr_id")
    private ReservationQR reservationQR;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;
    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime checkInTime;

    @Enumerated(EnumType.STRING)
    private Means means;

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setTableReturn(TableReturn tableReturn) {
        this.tableReturn = tableReturn;
    }

    public void changeReservationQR(ReservationQR reservationQR) {
        if (this.reservationQR != null) {
            this.reservationQR.changeReservation(null);
        }
        this.reservationQR = reservationQR;
        this.reservationQR.changeReservation(this);
    }

    public static Reservation createReservation(Member member, ProjectTable projectTable, QRImage qrImage, LocalDateTime startAt, LocalDateTime endAt) {
        Reservation reservation = Reservation.builder()
                .member(member)
                .projectTable(projectTable)
                .reservationStatus(RESERVATION_COMPLETED)
                .startAt(startAt)
                .endAt(endAt)
                .means(WEB)
                .build();

        reservation.changeReservationQR(ReservationQR.builder().qrImage(qrImage).build());

        return reservation;
    }

    public static Reservation createOnSiteReservation(Member member, ProjectTable projectTable, LocalDateTime startAt, LocalDateTime endAt) {
        return Reservation.builder()
                .member(member)
                .projectTable(projectTable)
                .reservationStatus(IN_USE)
                .startAt(startAt)
                .endAt(endAt)
                .checkInTime(LocalDateTime.now())
                .means(KIOSK)
                .build();
    }

    public void cancel(Long memberId) {
        if ((this.reservationStatus != RESERVATION_COMPLETED)
                || !memberId.equals(member.getMemberId())) {
            throw new UnableToCancelReservationException();
        }
        this.reservationStatus = CANCELED;
    }

    public void checkIn(boolean isPreviousReservationInUse) {
        if (isPreviousReservationInUse)
            throw new UnableToCheckInStatusException(); // 체크인 하려는 테이블이 현재 사용중이면 미리 체크인 불가능함.
        else if (LocalDateTime.now().isBefore(this.startAt.minusMinutes(POSSIBLE_CHECKIN_TIME_BEFORE.getValue())) //시작시간 10분이상 전에는 체크인 불가
                || LocalDateTime.now().isAfter(this.startAt.plusMinutes(POSSIBLE_CHECKIN_TIME_AFTER.getValue()))) { //시작시간 20분이 지난 후에는 체크인 불가
            throw new UnableToCheckInTimeException();
        }

        this.checkInTime = LocalDateTime.now();
        this.reservationStatus = IN_USE;
    }

}
