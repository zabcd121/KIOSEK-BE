package com.cse.cseprojectroommanagementserver.domain.penalty.api;

import com.cse.cseprojectroommanagementserver.domain.penalty.application.PenaltySearchService;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/admins/penalties")
@RequiredArgsConstructor
public class AdminPenaltyApiController {

    private final PenaltySearchService penaltySearchService;

    @GetMapping
    public ResponseSuccess<Page> getPenaltyList(PenaltySearchCondition searchCondition, Pageable pageable) {
        return new ResponseSuccess(PENALTY_LOGS_SEARCH_SUCCESS, penaltySearchService.searchPenaltyListByConditionAndPageable(searchCondition, pageable));
    }
}
