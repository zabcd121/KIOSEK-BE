package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRoomRepository extends JpaRepository<ProjectRoom, Long> {
    ProjectRoom findByRoomName(String roomName);
}
