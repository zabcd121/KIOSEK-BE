package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;

import java.util.List;

public interface ProjectRoomSearchableRepository {
    List<ProjectRoom> findAll();

    ProjectRoom findByRoomName(String roomName);
}
