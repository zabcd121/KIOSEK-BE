package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;

public interface TableDeactivationSearchableRepository {

    boolean existsBy(Long tableId, LocalDateTime startAt, LocalDateTime endAt);
    Page<AdminTableDeactivationSearchRes> findAllByPageable(Pageable pageable);
}
