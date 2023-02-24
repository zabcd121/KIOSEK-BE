package com.cse.cseprojectroommanagementserver.domain.complaint.repository;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintSearchableRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.QComplaint.*;
import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResDto.*;
import static com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.QProjectRoom.*;
import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResDto.*;

@Repository
@RequiredArgsConstructor
public class ComplaintSearchRepository implements ComplaintSearchableRepository {

    JPAQueryFactory queryFactory;

    @Override
    public Page<AdminComplaintSearchRes> findAllByPageable(Pageable pageable) {
        List<AdminComplaintSearchRes> content = queryFactory
                .select(Projections.fields(AdminComplaintSearchRes.class,
                        complaint.complaintId,
                        complaint.subject,
                        complaint.content,
                        complaint.image,
                        Projections.fields(SimpleProjectRoomRes.class, complaint.projectRoom.projectRoomId, complaint.projectRoom.buildingName, complaint.projectRoom.roomName)

                ))
                .from(complaint)
                .join(complaint.projectRoom, projectRoom)
                .orderBy(complaint.createdDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(complaint.count())
                .from(complaint);

        return PageableExecutionUtils.getPage(content,pageable,countQuery::fetchOne);
    }
}
