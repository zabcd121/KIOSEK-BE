package com.cse.cseprojectroommanagementserver.domain.penaltypolicy.api;

import com.cse.cseprojectroommanagementserver.domain.penaltypolicy.application.PenaltyPolicyChangeService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.penaltypolicy.dto.PenaltyPolicyRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.PENALTY_POLICY_CHANGE_SUCCESS;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.RESERVATION_POLICY_CHANGE_SUCCESS;

@RestController
@RequestMapping("/api/penalty/policy")
@RequiredArgsConstructor
public class PenaltyPolicyApiController {

    private final PenaltyPolicyChangeService penaltyPolicyChangeService;

    @PutMapping
    public ResponseSuccessNoResult changePenaltyPolicy(@RequestBody PenaltyPolicyChangeRequest penaltyPolicyChangeRequest) {
        penaltyPolicyChangeService.changePenaltyPolicy(penaltyPolicyChangeRequest);

        return new ResponseSuccessNoResult(PENALTY_POLICY_CHANGE_SUCCESS);
    }
}
