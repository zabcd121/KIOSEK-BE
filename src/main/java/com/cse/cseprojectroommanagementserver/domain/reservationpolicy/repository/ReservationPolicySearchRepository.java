package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.repository;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.QReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.ReservationPolicy;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.repository.ReservationPolicySearchableRepository;
import com.cse.cseprojectroommanagementserver.global.common.AppliedStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.domain.model.QReservationPolicy.*;
import static com.cse.cseprojectroommanagementserver.global.common.AppliedStatus.*;

@Repository
@RequiredArgsConstructor
public class ReservationPolicySearchRepository implements ReservationPolicySearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ReservationPolicy findCurrentlyPolicy() {
        return queryFactory.selectFrom(reservationPolicy)
                .where(reservationPolicy.appliedStatus.eq(CURRENTLY))
                .fetchOne();
    }
}
