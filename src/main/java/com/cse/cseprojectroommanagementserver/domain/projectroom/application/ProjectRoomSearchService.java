package com.cse.cseprojectroommanagementserver.domain.projectroom.application;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomRepository;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResponse.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectRoomSearchService {
    private final ProjectRoomRepository projectRoomRepository;
    private final ProjectTableRepository projectTableRepository;

    public List<ProjectRoomAndTableSearchResponse> searchAllProjectRoomAndTable() {
        List<ProjectRoomAndTableSearchResponse> projectRoomSearchList = new ArrayList<>();

        for (ProjectRoom projectRoom : projectRoomRepository.findAll()) {
            List<ProjectTable> projectTableList = projectTableRepository.findAllByProjectRoom(projectRoom);
            projectRoomSearchList.add(new ProjectRoomAndTableSearchResponse().of(projectRoom, projectTableList));
        }

        return projectRoomSearchList;
    }
}
