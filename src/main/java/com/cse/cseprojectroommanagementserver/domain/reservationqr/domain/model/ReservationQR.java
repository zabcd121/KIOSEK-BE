package com.cse.cseprojectroommanagementserver.domain.reservationqr.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.common.QRImage;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationQR extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long reservationQRId;

    @OneToOne(mappedBy = "reservationQR", fetch = FetchType.LAZY)
    private Reservation reservation;

    @Embedded
    private QRImage qrImage;

    public void changeReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
