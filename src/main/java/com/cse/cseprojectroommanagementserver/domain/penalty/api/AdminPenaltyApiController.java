package com.cse.cseprojectroommanagementserver.domain.penalty.api;

import com.cse.cseprojectroommanagementserver.domain.penalty.application.PenaltyImpositionService;
import com.cse.cseprojectroommanagementserver.domain.penalty.application.PenaltySearchService;
import com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltySearchCondition;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponseNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.penalty.dto.PenaltyResDto.*;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminPenaltyApiController {

    private final PenaltySearchService penaltySearchService;
    private final PenaltyImpositionService penaltyImpositionService;

    @GetMapping("/v1/penalties")
    public SuccessResponse<Page<SearchPenaltyByPagingRes>> getPenaltyList(@ModelAttribute PenaltySearchCondition searchCondition, Pageable pageable) {
        return new SuccessResponse(PENALTY_LOGS_SEARCH_SUCCESS, penaltySearchService.searchPenaltyListByConditionAndPageable(searchCondition, pageable));
    }

    @PostMapping("/v1/penalties")
    public SuccessResponseNoResult imposePenalty(@RequestBody @Validated PenaltyImpositionReq penaltyReq) {
        penaltyImpositionService.imposePenalty(penaltyReq);
        return new SuccessResponseNoResult(PENALTY_IMPOSITION_SUCCESS);
    }
}