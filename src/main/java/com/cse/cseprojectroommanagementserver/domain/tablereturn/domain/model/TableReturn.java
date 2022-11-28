package com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableReturn {

    @Id @GeneratedValue
    private Long tableReturnId;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation targetReservation;

    private File cleanUpPhoto;

    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime returnedDateTime;

    public void returnTable(){
        this.targetReservation.changeStatusToReturned(this);
        this.returnedDateTime = LocalDateTime.now();
    }


}
