package com.cse.cseprojectroommanagementserver.domain.projecttable.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTableSearchRepository extends JpaRepository<ProjectTable, Long> {
}
