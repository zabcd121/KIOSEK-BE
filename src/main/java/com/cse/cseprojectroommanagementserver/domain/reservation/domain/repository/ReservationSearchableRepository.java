package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto.*;

public interface ReservationSearchableRepository {
    List<SearchReservationResponse> findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(Long projectRoomId, LocalDateTime firstDateTime, LocalDateTime lastDateTime);

    List<CurrentReservationByMemberResponse> findCurrentAllByMemberId(Long memberId);
}
