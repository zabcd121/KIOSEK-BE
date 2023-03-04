package com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTableRepository extends JpaRepository<ProjectTable, Long> {
    List<ProjectTable> findAllByProjectRoom(ProjectRoom projectRoom);

    ProjectTable findByTableName(String tableName);
}
