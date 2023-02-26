package com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.NOT_RETURNED;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.RETURNED;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TableReturn {

    @Id @GeneratedValue
    private Long tableReturnId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation targetReservation;

    @Embedded
    private Image cleanUpPhoto;

    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime returnedDateTime;


    public static TableReturn createReturn(Reservation reservation, Image cleanUpPhoto) {
        TableReturn tableReturn = TableReturn.builder()
                .cleanUpPhoto(cleanUpPhoto)
                .returnedDateTime(LocalDateTime.now())
                .build();
        tableReturn.changeReservation(reservation);

        return tableReturn;
    }

    public static TableReturn createAutoReturn(Reservation reservation) {
        TableReturn tableReturn = TableReturn.builder()
                .returnedDateTime(LocalDateTime.now())
                .build();
        tableReturn.changeReservationByAutoReturn(reservation);

        return tableReturn;
    }

    private void changeReservation(Reservation reservation){
        if(this.targetReservation != null) {
            this.targetReservation.setTableReturn(null);
        }
        this.targetReservation = reservation;
        this.targetReservation.setTableReturn(this);
        this.targetReservation.setReservationStatus(RETURNED);
    }

    private void changeReservationByAutoReturn(Reservation reservation) {
        if(this.targetReservation != null) {
            this.targetReservation.setTableReturn(null);
        }
        this.targetReservation = reservation;
        this.targetReservation.setTableReturn(this);
        this.targetReservation.setReservationStatus(NOT_RETURNED);
    }

}
