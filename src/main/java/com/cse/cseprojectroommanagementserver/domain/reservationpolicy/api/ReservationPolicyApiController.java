package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicySearchService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.RESERVATION_POLICY_CHANGE_SUCCESS;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.RESERVATION_POLICY_SEARCH_SUCCESS;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationPolicyApiController {
    private final ReservationPolicySearchService reservationPolicySearchService;

    @GetMapping("/v1/reservations/policies")
    public ResponseSuccess getCurrentReservationPolicy() {
        return new ResponseSuccess(RESERVATION_POLICY_SEARCH_SUCCESS, reservationPolicySearchService.searchReservationPolicy());
    }
}
