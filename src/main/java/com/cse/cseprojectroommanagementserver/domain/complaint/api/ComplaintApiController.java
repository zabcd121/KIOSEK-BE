package com.cse.cseprojectroommanagementserver.domain.complaint.api;

import com.cse.cseprojectroommanagementserver.domain.complaint.application.ComplainService;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponseNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ComplaintApiController {

    private final ComplainService complainService;

    @PostMapping(value = "/v1/complaints", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    public SuccessResponseNoResult complain(@ModelAttribute ComplainReq complainReq) {
        complainService.complain(complainReq);
        return new SuccessResponseNoResult(COMPLAIN_SUCCESS);
    }
}
