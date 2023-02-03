package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.repository;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.QTableDeactivation.*;

@Repository
@RequiredArgsConstructor
public class TableDeactivationSearchRepository implements TableDeactivationSearchableRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public boolean existsBy(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        return queryFactory
                .from(tableDeactivation)
                .where(tableDeactivation.projectTable.tableId.eq(tableId)
                        .and(tableDeactivation.tableDeactivationInfo.startTime.lt(endAt))
                        .and(tableDeactivation.tableDeactivationInfo.endTime.gt(startAt)))
                .select(tableDeactivation.tableDeactivationId)
                .fetchFirst() != null;
    }
}
