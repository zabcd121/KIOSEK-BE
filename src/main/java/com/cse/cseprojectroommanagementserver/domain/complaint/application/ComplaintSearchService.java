package com.cse.cseprojectroommanagementserver.domain.complaint.application;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResponse.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComplaintSearchService {

    private final ComplaintSearchableRepository complaintSearchableRepository;

    public Page<AdminComplaintSearchResponse> searchComplaintListByPageable(Pageable pageable) {
        return complaintSearchableRepository.findAllByPageable(pageable);
    }
}
