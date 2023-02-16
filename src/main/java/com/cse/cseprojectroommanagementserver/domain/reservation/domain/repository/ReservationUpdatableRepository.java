package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationUpdatableRepository {

    void updateStatusBy(ReservationStatus reservationStatus, List<Long> projectTableIdList, LocalDateTime startAt, LocalDateTime endAt);
}
