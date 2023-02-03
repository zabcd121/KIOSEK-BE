package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivation;

import java.time.LocalDateTime;

public interface TableDeactivationSearchableRepository {

    boolean existsBy(Long tableId, LocalDateTime startAt, LocalDateTime endAt);
}
