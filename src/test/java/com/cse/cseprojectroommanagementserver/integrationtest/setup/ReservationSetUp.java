package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationRepository;
import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class ReservationSetUp {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation saveReservation(Member member, ProjectTable projectTable, LocalDateTime startAt, LocalDateTime endAt) {
        QRImage qrImage = QRImage.builder().fileOriName(member.getLoginId()).fileLocalName(member.getLoginId()).fileUrl("/test/").content("content").build();
        return reservationRepository.save(Reservation.createReservation(member, projectTable, qrImage, startAt, endAt));
    }

    public Reservation saveReservationWithStatus(ReservationStatus reservationStatus, Member member, ProjectTable projectTable, LocalDateTime startAt, LocalDateTime endAt) {
        QRImage qrImage = QRImage.builder().fileOriName(member.getLoginId()).fileLocalName(member.getLoginId()).fileUrl("/test/").content("content").build();
        Reservation createdReservation = Reservation.createReservation(member, projectTable, qrImage, startAt, endAt);
        createdReservation.setReservationStatus(reservationStatus);
        return reservationRepository.save(createdReservation);
    }

    @Transactional
    public void deleteAll() {
        reservationRepository.deleteAll();
    }
}
