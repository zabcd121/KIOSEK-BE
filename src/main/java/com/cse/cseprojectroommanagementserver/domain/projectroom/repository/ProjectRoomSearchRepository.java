package com.cse.cseprojectroommanagementserver.domain.projectroom.repository;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.QProjectTable;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom.*;
import static com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.QProjectTable.*;

@Repository
@RequiredArgsConstructor
public class ProjectRoomSearchRepository implements ProjectRoomSearchableRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProjectRoom> findAll() {
        return queryFactory
                .selectFrom(projectRoom)
                .join(projectRoom.projectTableList, projectTable).fetchJoin()
                .fetch();
    }

    @Override
    public ProjectRoom findByRoomName(String roomName) {
        return queryFactory
                .selectFrom(projectRoom)
                .where(projectRoom.roomName.eq(roomName))
                .fetchOne();
    }
}
