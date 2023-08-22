package com.cse.cseprojectroommanagementserver.domain.projectroom.application;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectRoomSearchService {
    private final ProjectRoomSearchableRepository projectRoomSearchableRepository;

    public List<ProjectRoomAndTableSearchRes> searchAllProjectRoomAndTable() {
        List<ProjectRoomAndTableSearchRes> projectRoomSearchList = new ArrayList<>();

        projectRoomSearchableRepository.findAll().forEach(projectRoom -> {
            List<ProjectTable> projectTableList = projectRoom.getProjectTableList();
            projectRoomSearchList.add(ProjectRoomAndTableSearchRes.of(projectRoom, projectTableList));
        });

        return projectRoomSearchList;
    }
}
