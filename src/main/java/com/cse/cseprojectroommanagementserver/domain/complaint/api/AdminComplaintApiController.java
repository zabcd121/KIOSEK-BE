package com.cse.cseprojectroommanagementserver.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.domain.complaint.application.ComplaintSearchService;
import com.cse.cseprojectroommanagementserver.global.dto.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintResDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminComplaintApiController {

    private final ComplaintSearchService complaintSearchService;

    @GetMapping("/v1/complaints")
    public ResponseSuccess<Page<AdminComplaintSearchRes>> getComplaintList(Pageable pageable) {
        return new ResponseSuccess<>(COMPLAINT_SEARCH_SUCCESS, complaintSearchService.searchComplaintListByPageable(pageable));
    }
}
