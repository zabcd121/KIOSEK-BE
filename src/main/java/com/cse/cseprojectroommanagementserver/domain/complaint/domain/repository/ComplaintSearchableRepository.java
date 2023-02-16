package com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResponse.*;

public interface ComplaintSearchableRepository {

    Page<AdminComplaintSearchResponse> findAllByPageable(Pageable pageable);
}
