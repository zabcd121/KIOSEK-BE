package com.cse.cseprojectroommanagementserver.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.domain.complaint.application.ComplainService;
import com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintRequest;
import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintRequest.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintApiController {

    private final ComplainService complainService;

    @PostMapping
    public ResponseSuccessNoResult complain(ComplainRequest complainRequest) {
        complainService.complain(complainRequest);
        return new ResponseSuccessNoResult(COMPLAIN_SUCCESS);
    }
}
