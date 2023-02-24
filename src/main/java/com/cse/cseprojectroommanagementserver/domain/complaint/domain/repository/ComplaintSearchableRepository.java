package com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResDto.*;

public interface ComplaintSearchableRepository {

    Page<AdminComplaintSearchRes> findAllByPageable(Pageable pageable);
}
