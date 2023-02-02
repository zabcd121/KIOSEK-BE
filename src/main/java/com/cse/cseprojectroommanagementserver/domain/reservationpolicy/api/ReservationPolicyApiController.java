package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicySearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.RESERVATION_POLICY_CHANGE_SUCCESS;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.RESERVATION_POLICY_SEARCH_SUCCESS;

@RestController
@RequestMapping("/api/reservation/policy")
@RequiredArgsConstructor
public class ReservationPolicyApiController {

    private final ReservationPolicyChangeService reservationPolicyChangeService;
    private final ReservationPolicySearchService reservationPolicySearchService;

    @PutMapping
    public ResponseSuccessNoResult changeReservationPolicy(@RequestBody ReservationPolicyChangeRequest reservationPolicyChangeRequest) {
        reservationPolicyChangeService.changeReservationPolicy(reservationPolicyChangeRequest);

        return new ResponseSuccessNoResult(RESERVATION_POLICY_CHANGE_SUCCESS);
    }

    @GetMapping
    public ResponseSuccess getCurrentReservationPolicy() {
        return new ResponseSuccess(RESERVATION_POLICY_SEARCH_SUCCESS, reservationPolicySearchService.searchReservationPolicy());
    }
}
