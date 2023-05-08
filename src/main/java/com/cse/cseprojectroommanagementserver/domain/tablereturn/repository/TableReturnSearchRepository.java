package com.cse.cseprojectroommanagementserver.domain.tablereturn.repository;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.QTableReturn;
import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository.TableReturnSearchableRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.cse.cseprojectroommanagementserver.domain.reservation.domain.model.QReservation.*;
import static com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.QTableReturn.*;

@Repository
@RequiredArgsConstructor
public class TableReturnSearchRepository implements TableReturnSearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByReservationId(Long reservationId) {
        return queryFactory
                .from(tableReturn)
                .where(tableReturn.reservation.reservationId.eq(reservationId))
                .select(tableReturn.tableReturnId)
                .fetchFirst() != null;
    }

    /**
     * return queryFactory
     *                 .from(reservation)
     *                 .where(reservation.projectTable.tableName.eq(tableName)
     *                         .and(reservation.reservationStatus.eq(IN_USE)))
     *                 .select(reservation.reservationId)
     *                 .fetchFirst() != null;
     */
}
