package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository.ProjectTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectTableSetUp {
    @Autowired
    private ProjectTableRepository projectTableRepository;

    public ProjectTable saveProjectTable(ProjectRoom projectRoom, String tableName) {
        return projectTableRepository.save(
                ProjectTable.builder()
                        .tableName(tableName)
                        .projectRoom(projectRoom)
                        .build()
        );
    }
}
