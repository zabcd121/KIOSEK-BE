package com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.tablereturn.domain.model.TableReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableReturnRepository extends JpaRepository<TableReturn, Long> {
}
