package com.cse.cseprojectroommanagementserver.domain.reservation.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;

@Repository
@RequiredArgsConstructor
public class ReservationVerificationRepository implements ReservationVerifiableRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsBy(Long projectTableId, LocalDateTime startAt, LocalDateTime endAt) {
        return queryFactory
                .from(reservation)
                .where(reservation.projectTable.tableId.eq(projectTableId)
                        .and(reservation.reservationStatus.notIn(CANCELED, RETURNED, UN_USED))
                        .and(reservation.startAt.lt(endAt))
                        .and(reservation.endAt.gt(startAt))

                )
                .select(reservation.reservationId)
                .fetchFirst() != null;
    }

    @Override
    public Long countCreatedReservationForTodayByMemberId(Long memberId) {
        return queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(reservation.member.memberId.eq(memberId)
                        .and(reservation.createdDateTime.startsWith(
                                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString()))
                        .and(reservation.reservationStatus.ne(CANCELED)))
                .fetchOne();
    }

    @Override
    public boolean existsInUsePreviousReservation(Long tableId, LocalDateTime startAt) {
        return queryFactory
                .from(reservation)
                .where(reservation.projectTable.tableId.eq(tableId)
                        .and(reservation.endAt.eq(startAt).and(reservation.reservationStatus.eq(IN_USE))))
                .select(reservation.reservationId)
                .fetchFirst() != null;
    }

}