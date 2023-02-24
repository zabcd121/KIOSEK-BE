package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.repository;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom.*;
import static com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.QProjectTable.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.QTableDeactivation.*;
import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;

@Repository
@RequiredArgsConstructor
public class TableDeactivationSearchRepository implements TableDeactivationSearchableRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public boolean existsBy(Long tableId, LocalDateTime startAt, LocalDateTime endAt) {
        return queryFactory
                .from(tableDeactivation)
                .where(tableDeactivation.projectTable.tableId.eq(tableId)
                        .and(tableDeactivation.tableDeactivationInfo.startAt.lt(endAt))
                        .and(tableDeactivation.tableDeactivationInfo.endAt.gt(startAt)))
                .select(tableDeactivation.tableDeactivationId)
                .fetchFirst() != null;
    }

    @Override
    public Page<AdminTableDeactivationSearchRes> findAllByPageable(Pageable pageable) {
        List<AdminTableDeactivationSearchRes> content = queryFactory
                .select(Projections.fields(AdminTableDeactivationSearchRes.class,
                        tableDeactivation.projectTable.projectRoom.roomName,
                        tableDeactivation.projectTable.tableName,
                        tableDeactivation.tableDeactivationInfo.startAt,
                        tableDeactivation.tableDeactivationInfo.endAt,
                        tableDeactivation.tableDeactivationInfo.reason
                ))
                .from(tableDeactivation)
                .join(tableDeactivation.projectTable, projectTable)
                .join(tableDeactivation.projectTable.projectRoom, projectRoom)
                .orderBy(tableDeactivation.tableDeactivationInfo.startAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(tableDeactivation.count())
                .from(tableDeactivation);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
