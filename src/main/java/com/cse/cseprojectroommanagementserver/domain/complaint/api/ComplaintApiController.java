package com.cse.cseprojectroommanagementserver.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.domain.complaint.application.ComplainService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ComplaintApiController {

    private final ComplainService complainService;

    @PostMapping(value = "/v1/complaints", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseSuccessNoResult complain(ComplainReq complainReq) {
        complainService.complain(complainReq);
        return new ResponseSuccessNoResult(COMPLAIN_SUCCESS);
    }
}
