package com.cse.cseprojectroommanagementserver.domain.reservation.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationUpdatableRepository;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.*;

@Repository
@RequiredArgsConstructor
public class ReservationUpdateRepository implements ReservationUpdatableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void updateStatusBy(ReservationStatus reservationStatus, List<Long> projectTableIdList, LocalDateTime startAt, LocalDateTime endAt) {
        queryFactory.update(reservation)
                .set(reservation.reservationStatus, reservationStatus)
                .where(reservation.projectTable.tableId.in(projectTableIdList)
                        .and(reservation.startDateTime.lt(endAt))
                        .and(reservation.endDateTime.gt(startAt)))
                .execute();
    }
}
