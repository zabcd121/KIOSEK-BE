package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.api;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application.PenaltyPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application.PenaltyPolicySearchService;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponseNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyResDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminPenaltyPolicyApiController {

    private final PenaltyPolicyChangeService penaltyPolicyChangeService;
    private final PenaltyPolicySearchService penaltyPolicySearchService;

    @PutMapping("/v1/penalties/policies")
    public SuccessResponseNoResult changePenaltyPolicy(@RequestBody @Validated PenaltyPolicyChangeReq penaltyPolicyChangeReq) {
        penaltyPolicyChangeService.changePenaltyPolicy(penaltyPolicyChangeReq);
        return new SuccessResponseNoResult(PENALTY_POLICY_CHANGE_SUCCESS);
    }

    @GetMapping("/v1/penalties/policies")
    public SuccessResponse<PenaltyPolicySearchRes> getCurrentPenaltyPolicy() {
        return new SuccessResponse(PENALTY_POLICY_SEARCH_SUCCESS, penaltyPolicySearchService.searchPenaltyPolicy());
    }
}
