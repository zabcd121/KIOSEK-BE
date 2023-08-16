package com.cse.cseprojectroommanagementserver.domain.reservation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservationqr.domain.model.ReservationQR;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.dto.ReservationFixedPolicy;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.BusinessRuleException;
import com.cse.cseprojectroommanagementserver.global.error.exception.PolicyInfractionException;
import com.cse.cseprojectroommanagementserver.global.error.exception.UnAuthorizedException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Means.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = {
        @Index(name = "ix_start_at", columnList = "start_at"),
        @Index(name = "ix_end_at", columnList = "end_at")})
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_table_id", nullable = false)
    private ProjectTable projectTable;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    private TableReturn tableReturn;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_qr_id")
    private ReservationQR reservationQR;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private Means means;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = true)
    private LocalDateTime checkInTime;

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

    public void cancel(Long triedMemberId) {
        if ((this.reservationStatus != RESERVATION_COMPLETED))
            throw new BusinessRuleException(ErrorCode.IRREVOCABLE_RESERVATION_STATUS);
        if (!triedMemberId.equals(member.getMemberId()))
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED_RESERVATION);

        this.reservationStatus = CANCELED;
    }

    public void checkIn(boolean isPreviousMemberInUse) {
        if (isPreviousMemberInUse) throw new BusinessRuleException(ErrorCode.CHECKIN_IMPOSSIBLE_STATUS); // 체크인 하려는 테이블이 현재 사용중이면 미리 체크인 불가능함.
        else if (LocalDateTime.now().isBefore(
                this.startAt.minusMinutes(ReservationFixedPolicy.POSSIBLE_CHECKIN_TIME_BEFORE.getValue())) //시작시간 20분이상 전에는 체크인 불가
                || LocalDateTime.now().isAfter(
                this.startAt.plusMinutes(ReservationFixedPolicy.POSSIBLE_CHECKIN_TIME_AFTER.getValue()))) { //시작시간 20분이 지난 후에는 체크인 불가
            throw new PolicyInfractionException(ErrorCode.CHECKIN_TIME_POLICY_INFRACTION);
        }

        this.checkInTime = LocalDateTime.now();
        this.reservationStatus = IN_USE;
    }

}
