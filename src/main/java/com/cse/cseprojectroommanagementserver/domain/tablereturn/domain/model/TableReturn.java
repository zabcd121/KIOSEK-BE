package com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.NOT_RETURNED;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.RETURNED;
import static com.cse.cseprojectroommanagementserver.global.formatter.DateFormatProvider.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = @Index(name = "ix_reservation_id", columnList = "reservation_id"))
public class TableReturn extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableReturnId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, unique = true)
    private Reservation reservation;

    @Embedded
    private Image cleanUpPhoto;

    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime returnedAt;


    public static TableReturn createReturn(Reservation reservation, Image cleanUpPhoto) {
        TableReturn tableReturn = TableReturn.builder()
                .cleanUpPhoto(cleanUpPhoto)
                .returnedAt(LocalDateTime.now())
                .build();
        tableReturn.changeReservation(reservation);

        return tableReturn;
    }

    public static TableReturn createAutoReturn(Reservation reservation) {
        TableReturn tableReturn = TableReturn.builder()
                .returnedAt(LocalDateTime.now())
                .build();
        tableReturn.changeReservationByAutoReturn(reservation);

        return tableReturn;
    }

    private void changeReservation(Reservation reservation){
        if(this.reservation != null) {
            this.reservation.changeTableReturn(null);
        }
        this.reservation = reservation;
        this.reservation.changeTableReturn(this);
        this.reservation.changeReservationStatus(RETURNED);
    }

    private void changeReservationByAutoReturn(Reservation reservation) {
        if(this.reservation != null) {
            this.reservation.changeTableReturn(null);
        }
        this.reservation = reservation;
        this.reservation.changeTableReturn(this);
        this.reservation.changeReservationStatus(NOT_RETURNED);
    }

}
