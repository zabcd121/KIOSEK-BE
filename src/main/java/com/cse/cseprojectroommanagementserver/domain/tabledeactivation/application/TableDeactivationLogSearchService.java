package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.application;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationSearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto.TableDeactivationResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TableDeactivationLogSearchService {

    private final TableDeactivationSearchableRepository tableDeactivationSearchableRepository;

    public Page<AdminTableDeactivationSearchRes> searchTableDeactivationLog(Pageable pageable) {

        return tableDeactivationSearchableRepository.findAllByPageable(pageable);
    }
}
