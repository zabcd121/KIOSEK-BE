package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import java.time.LocalDateTime;

public interface ReservationVerifiableRepository {

    boolean existsBy(Long projectTableId, LocalDateTime startAt, LocalDateTime endAt);

    Long countCreatedReservationForTodayByMemberId(Long memberId);

    boolean existsInUsePreviousReservation(Long tableId, LocalDateTime startAt);
}
