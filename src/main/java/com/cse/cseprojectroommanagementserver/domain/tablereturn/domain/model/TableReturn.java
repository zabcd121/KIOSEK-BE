package com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableReturn {

    @Id @GeneratedValue
    private Long tableReturnId;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation targetReservation;

    private File cleanUpPhoto;

    private LocalDateTime returnedTime;


}
