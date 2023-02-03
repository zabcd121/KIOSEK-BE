package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableDeactivationRepository extends JpaRepository<TableDeactivation, Long> {
}
