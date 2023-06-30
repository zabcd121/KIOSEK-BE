package com.cse.cseprojectroommanagementserver.domain.visitor.api;

import com.cse.cseprojectroommanagementserver.domain.visitor.application.VisitorSearchService;
import com.cse.cseprojectroommanagementserver.global.success.SuccessCode;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.visitor.dto.VisitorReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class VisitorApiController {

    private final VisitorSearchService visitorSearchService;

    @GetMapping("/v1/visitors/count")
    public SuccessResponse<Integer> getVisitorCount(@ModelAttribute VisitorCountDuringPeriodReq visitorCountDuringPeriodReq) {
        return new SuccessResponse<>(VISIOR_COUNT_SEARCH_SUCCESS, visitorSearchService.searchVisitorCount(visitorCountDuringPeriodReq));
    }

}
