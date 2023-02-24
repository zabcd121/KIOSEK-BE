package com.cse.cseprojectroommanagementserver.domain.complaint.application;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintSearchableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComplaintSearchService {

    private final ComplaintSearchableRepository complaintSearchableRepository;

    public Page<AdminComplaintSearchRes> searchComplaintListByPageable(Pageable pageable) {
        return complaintSearchableRepository.findAllByPageable(pageable);
    }
}
