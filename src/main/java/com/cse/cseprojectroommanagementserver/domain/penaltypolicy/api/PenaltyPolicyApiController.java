package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.api;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application.PenaltyPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application.PenaltyPolicySearchService;
import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyResponseDto;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/penalties/policy")
@RequiredArgsConstructor
public class PenaltyPolicyApiController {

    private final PenaltyPolicyChangeService penaltyPolicyChangeService;
    private final PenaltyPolicySearchService penaltyPolicySearchService;

    @PutMapping
    public ResponseSuccessNoResult changePenaltyPolicy(@RequestBody PenaltyPolicyChangeRequest penaltyPolicyChangeRequest) {
        penaltyPolicyChangeService.changePenaltyPolicy(penaltyPolicyChangeRequest);

        return new ResponseSuccessNoResult(PENALTY_POLICY_CHANGE_SUCCESS);
    }

    @GetMapping
    public ResponseSuccess getCurrentPenaltyPolicy() {
        return new ResponseSuccess(PENALTY_POLICY_SEARCH_SUCCESS,
                penaltyPolicySearchService.searchPenaltyPolicy());
    }
}
