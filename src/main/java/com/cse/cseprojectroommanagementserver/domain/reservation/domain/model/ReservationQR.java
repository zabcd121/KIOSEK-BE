package com.cse.cseprojectroommanagementserver.domain.reservation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationQR {
    @Id
    @GeneratedValue
    private Long reservationQRId;

    @OneToOne(mappedBy = "reservationQR", fetch = FetchType.LAZY)
    private Reservation reservation;

    @Embedded
    private Image image;

    public void changeReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
