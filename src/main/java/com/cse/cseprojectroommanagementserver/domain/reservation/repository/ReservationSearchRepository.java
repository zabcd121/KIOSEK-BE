package com.cse.cseprojectroommanagementserver.domain.reservation.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservationQR;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom.*;
import static com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.QProjectTable.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservationQR.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.QTableReturn.*;

@Repository
@RequiredArgsConstructor
public class ReservationSearchRepository implements ReservationSearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchReservationResponse> findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(Long projectRoomId, LocalDateTime firstDateTime, LocalDateTime lastDateTime) {
        return queryFactory
                .select(Projections.bean(SearchReservationResponse.class,
                        reservation.projectTable.tableId,
                        reservation.startDateTime, reservation.endDateTime, tableReturn.returnedDateTime))
                .from(reservation)
                .join(reservation.tableReturn, tableReturn).fetchJoin()
                .on(reservation.projectTable.projectRoom.projectRoomId.eq(projectRoomId).
                        and(reservation.startDateTime.between(firstDateTime, lastDateTime))
                        .and(reservation.reservationStatus.notIn(CANCELED, UN_USED)))
                .fetch();
    }

    @Override
    public List<CurrentReservationByMemberResponse> findCurrentAllByMemberId(Long memberId) {
        return queryFactory
                .select(Projections.fields(CurrentReservationByMemberResponse.class,
                        reservation.reservationId, reservation.startDateTime, reservation.endDateTime,
                        reservation.reservationStatus, projectRoom.roomName, projectTable.tableNo,
                        reservationQR.image.fileLocalName.as("imageName"), reservationQR.image.fileUrl.as("imageURL")))
                .from(reservation)
                .join(reservation.projectTable, projectTable)
                .join(projectTable.projectRoom, projectRoom)
                .join(reservation.reservationQR, reservationQR)
                .where(reservation.member.memberId.eq(memberId)
                        .and(reservation.reservationStatus.in(RESERVATION_COMPLETED, IN_USE, RETURN_WAITING)))
                .orderBy(reservation.startDateTime.asc())
                .fetch();
    }


}
