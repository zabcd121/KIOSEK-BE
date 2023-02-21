package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import java.time.LocalDateTime;

public interface ReservationVerifiableRepository {

    boolean existsBy(Long projectTableId, LocalDateTime startAt, LocalDateTime endAt);

    Long countCreatedReservationForTodayBy(Long memberId);

    boolean existsCurrentlyInUseTableBy(Long tableId, LocalDateTime startAt);
}
