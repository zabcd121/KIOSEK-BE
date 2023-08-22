package com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto;
import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.Reservation;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.dto.TableReturnResDto;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cse.cseprojectroommanagementserver.domain.member.domain.model.QMember.member;
import static com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom.projectRoom;
import static com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.QProjectTable.projectTable;
import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.reservation;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.QTableReturn.tableReturn;

public interface ReservationSearchableRepository {
    List<ReservationSearchRes> findAllByProjectRoomIdAndBetweenFirstAtAndLastAt(Long projectRoomId, LocalDateTime firstAt, LocalDateTime lastAt);

    List<CurrentReservationByMemberRes> findCurrentAllByMemberId(Long memberId);

    List<PastReservationByMemberRes> findPastAllByMemberId(Long memberId);

    Optional<Reservation> findByReservationId(Long reservationId);

    Optional<Reservation> findByQRContents(String contents);

    Optional<List<Reservation>> findReturnWaitingReservations();

    Optional<List<Reservation>> findFinishedButInUseStatusReservations();

    boolean existsCurrentInUseReservationByTableName(String tableName);

    Long countPastReservations(Long memberId);

    List<Reservation> findUnUsedReservations();

    Page<SearchReservationByPagingRes> findAllByConditionAndPageable(ReservationSearchCondition condition, Pageable pageable, Long count);

    Long countByCondition(ReservationSearchCondition condition);
}
