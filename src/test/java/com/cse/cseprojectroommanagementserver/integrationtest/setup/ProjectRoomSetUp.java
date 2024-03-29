package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomRepository;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomSearchableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectRoomSetUp {
    @Autowired
    ProjectRoomRepository projectRoomRepository;

    @Autowired
    ProjectRoomSearchableRepository projectRoomSearchableRepository;

    public ProjectRoom saveProjectRoom(String buildingName, String roomName, Integer priority) {
        return projectRoomRepository.save(
                ProjectRoom.builder()
                        .buildingName(buildingName)
                        .roomName(roomName)
                        .priority(priority)
                        .build()
        );
    }

    public ProjectRoom findProjectRoomByRoomName(String roomName) {
        return projectRoomSearchableRepository.findByRoomName(roomName);
    }
}
