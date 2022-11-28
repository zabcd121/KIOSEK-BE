package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}