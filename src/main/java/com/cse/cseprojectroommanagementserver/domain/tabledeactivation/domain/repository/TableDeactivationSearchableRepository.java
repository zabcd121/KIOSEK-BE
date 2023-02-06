package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivation;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResponseDto.*;

public interface TableDeactivationSearchableRepository {

    boolean existsBy(Long tableId, LocalDateTime startAt, LocalDateTime endAt);
    Page<SearchTableDeactivationListResponse> findAllByPageable(Pageable pageable);
}
