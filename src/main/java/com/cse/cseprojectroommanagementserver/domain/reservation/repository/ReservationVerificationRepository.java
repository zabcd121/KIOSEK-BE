package com.cse.cseprojectroommanagementserver.domain.reservation.repository;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.QAccountQR;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.QMember;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationVerifiableRepository;
import com.cse.cseprojectroommanagementserver.global.util.NullSafeUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.QAccountQR.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.global.util.NullSafeUtil.*;

@Repository
@RequiredArgsConstructor
public class ReservationVerificationRepository implements ReservationVerifiableRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsBy(Long projectTableId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return queryFactory
                .from(reservation)
                .where(reservation.projectTable.tableId.eq(projectTableId)
                        .and(reservation.reservationStatus.notIn(CANCELED)
                                .and(startBetweenReqStartAndEnd(startDateTime, endDateTime)
                                        .or(endBetweenReqStartAndEnd(startDateTime, endDateTime)
                                                .or(startGoeReqStartAndEndLoeReqEnd(startDateTime, endDateTime)))
                                ))

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
    public boolean existsInUsePreviousReservation(Long tableId, LocalDateTime startDateTime) {
        return queryFactory
                .from(reservation)
                .where(reservation.projectTable.tableId.eq(tableId)
                        .and(reservation.endDateTime.eq(startDateTime).and(reservation.reservationStatus.eq(IN_USE))))
                .select(reservation.reservationId)
                .fetchFirst() != null;
    }


    private BooleanBuilder startBetweenReqStartAndEnd(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return nullSafeBuilder(() -> reservation.startDateTime.between(startDateTime, endDateTime));
    }

    private BooleanBuilder endBetweenReqStartAndEnd(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return nullSafeBuilder(() -> reservation.endDateTime.between(startDateTime, endDateTime)
                .and(reservation.endDateTime.ne(startDateTime)));
    }

    private BooleanBuilder startGoeReqStartAndEndLoeReqEnd(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return nullSafeBuilder(() -> reservation.startDateTime.goe(startDateTime).and(reservation.endDateTime.loe(endDateTime)));
    }

}