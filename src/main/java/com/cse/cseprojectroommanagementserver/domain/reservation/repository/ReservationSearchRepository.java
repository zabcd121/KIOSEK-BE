package com.cse.cseprojectroommanagementserver.domain.reservation.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.QMember.*;
import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom.*;
import static com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.QProjectTable.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservationQR.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.ReservationStatus.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.QTableReturn.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnResDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.ReservationFixedPolicy.*;
import static org.springframework.util.StringUtils.*;

@Repository
@RequiredArgsConstructor
public class ReservationSearchRepository implements ReservationSearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchReservationRes> findAllByProjectRoomIdAndBetweenFirstDateTimeAndLastDateTime(Long projectRoomId, LocalDateTime firstAt, LocalDateTime lastAt) {
        return queryFactory
                .select(Projections.fields(SearchReservationRes.class,
                        reservation.projectTable.tableId.as("projectTableId"), projectTable.tableName,
                        reservation.startAt, reservation.endAt, tableReturn.returnedDateTime))
                .from(reservation)
                .leftJoin(reservation.tableReturn, tableReturn)
                .join(reservation.projectTable, projectTable)
                .where(reservation.projectTable.projectRoom.projectRoomId.eq(projectRoomId)
                        .and(reservation.startAt.between(firstAt, lastAt)
                                .and(reservation.reservationStatus.notIn(CANCELED))))
                .fetch();
    }

    @Override
    public List<CurrentReservationByMemberRes> findCurrentAllByMemberId(Long memberId) {
        return queryFactory
                .select(Projections.fields(CurrentReservationByMemberRes.class,
                        reservation.reservationId, reservation.startAt, reservation.endAt, tableReturn.returnedDateTime,
                        reservation.reservationStatus, projectRoom.roomName, projectTable.tableName,
                        reservationQR.qrImage.fileLocalName.as("imageName"), reservationQR.qrImage.fileUrl.as("imageURL")))
                .from(reservation)
                .leftJoin(reservation.tableReturn, tableReturn)
                .join(reservation.projectTable, projectTable)
                .join(reservation.projectTable.projectRoom, projectRoom)
                .leftJoin(reservation.reservationQR, reservationQR)
                .where(reservation.member.memberId.eq(memberId)
                        .and(reservation.reservationStatus.in(RESERVATION_COMPLETED, IN_USE, RETURN_WAITING)))
                .orderBy(reservation.startAt.asc())
                .fetch();
    }

    @Override
    public List<PastReservationByMemberRes> findPastAllByMemberId(Long memberId) {
        return queryFactory
                .select(Projections.fields(PastReservationByMemberRes.class,
                        reservation.reservationId, reservation.startAt, reservation.endAt, tableReturn.returnedDateTime,
                        reservation.reservationStatus, projectRoom.roomName, projectTable.tableName))
                .from(reservation)
                .leftJoin(reservation.tableReturn, tableReturn)
                .join(reservation.projectTable, projectTable)
                .join(projectTable.projectRoom, projectRoom)
                .where(reservation.member.memberId.eq(memberId)
                        .and(reservation.reservationStatus.in(UN_USED, RETURN_WAITING, NOT_RETURNED, RETURNED, CANCELED)))
                .orderBy(reservation.startAt.desc())
                .fetch();
    }

    @Override
    public Optional<Reservation> findByReservationId(Long reservationId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(reservation)
                        .where(reservation.reservationId.eq(reservationId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Reservation> findByQRContents(String contents) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(reservation)
                        .join(reservation.reservationQR, reservationQR).fetchJoin()
                        .join(reservation.projectTable, projectTable).fetchJoin()
                        .where(reservation.reservationStatus.eq(RESERVATION_COMPLETED)
                                .and(reservationQR.qrImage.content.eq(contents)))
                        .fetchOne()
        );
    }

    @Override
    public Optional<List<Reservation>> findReturnWaitingReservations() {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(reservation)
                        .where(reservation.reservationStatus.eq(RETURN_WAITING))
                        .fetch()
        );
    }

    @Override
    public Optional<List<Reservation>> findFinishedButInUseStatusReservations() {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(reservation)
                        .where(reservation.reservationStatus.eq(IN_USE)
                                .and(reservation.endAt.before(LocalDateTime.now())))
                        .fetch()
        );
    }

    @Override
    public boolean existsCurrentInUseReservationByTableName(String tableName) {
        return queryFactory
                .from(reservation)
                .where(reservation.projectTable.tableName.eq(tableName)
                        .and(reservation.reservationStatus.eq(IN_USE)))
                .select(reservation.reservationId)
                .fetchFirst() != null;
    }

    @Override
    public Long countPastReservations(Long memberId) {
        return queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(reservation.reservationStatus.in(CANCELED, UN_USED, NOT_RETURNED, RETURNED)
                        .and(reservation.member.memberId.eq(memberId)))
                .fetchOne();
    }

    @Override
    public List<Reservation> findUnUsedReservations() {
        LocalDateTime current = LocalDateTime.now();
        return queryFactory
                .selectFrom(reservation)
                .where(reservation.reservationStatus.eq(RESERVATION_COMPLETED)
                        .and(reservation.startAt.eq(LocalDateTime.of(current.getYear(), current.getMonthValue(), current.getDayOfMonth(), current.getHour(), current.getMinute() - POSSIBLE_CHECKIN_TIME_AFTER.getValue()))))
                .fetch();
    }

    @Override
    public Page<SearchReservationByPagingRes> findAllByConditionAndPageable(ReservationSearchCondition condition, Pageable pageable) {
        List<SearchReservationByPagingRes> content = queryFactory
                .select(Projections.fields(SearchReservationByPagingRes.class,
                            Projections.fields(ReservationSimpleInfoRes.class, reservation.reservationId, reservation.startAt, reservation.endAt, reservation.reservationStatus).as("reservation"),
                            Projections.fields(TableReturnSimpleInfoRes.class, reservation.tableReturn.tableReturnId, reservation.tableReturn.returnedDateTime, reservation.tableReturn.cleanUpPhoto).as("tableReturn"),
                            Projections.fields(MemberSimpleInfoRes.class, reservation.member.memberId, reservation.member.name, reservation.member.account.loginId).as("member"),
                            reservation.projectTable.projectRoom.roomName,
                            reservation.projectTable.tableName
                ))
                .from(reservation)
                .join(reservation.tableReturn, tableReturn)
                .join(reservation.member, member)
                .join(reservation.projectTable, projectTable)
                .join(reservation.projectTable.projectRoom, projectRoom)
                .where(
                        periodBetween(condition.getStartDt(), condition.getEndDt()),
                        memberNameEq(condition.getMemberName()),
                        loginIdEq(condition.getLoginId()),
                        reservationStatusEq(condition.getReservationStatus()),
                        roomNameEq(condition.getRoomName())
                )
                .orderBy(reservation.startAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(reservation.count())
                .from(reservation)
                .join(reservation.tableReturn, tableReturn)
                .join(reservation.member, member)
                .join(reservation.projectTable, projectTable)
                .join(reservation.projectTable.projectRoom, projectRoom)
                .where(
                        periodBetween(condition.getStartDt(), condition.getEndDt()),
                        memberNameEq(condition.getMemberName()),
                        loginIdEq(condition.getLoginId()),
                        reservationStatusEq(condition.getReservationStatus()),
                        roomNameEq(condition.getRoomName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);


    }

    private BooleanExpression periodBetween(LocalDate startDt, LocalDate endDt) {
        return (startDt != null && endDt != null) ?
                reservation.startAt.goe(startDt.atStartOfDay())
                        .and(reservation.endAt.loe(endDt.atTime(LocalTime.MAX)))
                : null;
    }

    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? reservation.member.name.eq(memberName) : null;
    }

    private BooleanExpression loginIdEq(String loginId) {
        return hasText(loginId) ? reservation.member.account.loginId.eq(loginId) : null;
    }

    private BooleanExpression reservationStatusEq(String reservationStatus) {
        return hasText(reservationStatus) ? reservation.reservationStatus.eq(ReservationStatus.ofStatus(reservationStatus)) : null;
    }

    private BooleanExpression roomNameEq(String roomName) {
        return hasText(roomName) ? reservation.projectTable.projectRoom.roomName.eq(roomName) : null;
    }

}
