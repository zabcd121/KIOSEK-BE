package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto.*;

public interface ReservationSearchableRepository {
    List<SearchReservationResponse> findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(Long projectRoomId, LocalDateTime firstDateTime, LocalDateTime lastDateTime);

    List<CurrentReservationByMemberResponse> findCurrentAllByMemberId(Long memberId);

    List<PastReservationByMemberResponse> findPastAllByMemberId(Long memberId);

    Optional<Reservation> findByReservationId(Long reservationId);

    Optional<Reservation> findByQRContents(String contents);

    Optional<List<Reservation>> findReturnWaitingReservations();

    Optional<List<Reservation>> findFinishedReservations();

    boolean existsCurrentInUseReservationByTableName(String tableName);
}
