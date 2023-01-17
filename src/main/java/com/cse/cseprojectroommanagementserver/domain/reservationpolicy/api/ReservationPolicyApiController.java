package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicyChangeService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyRequestDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.RESERVATION_POLICY_CHANGE_FAIL;

@RestController
@RequestMapping("/api/reservation/policy")
@RequiredArgsConstructor
public class ReservationPolicyApiController {

    private final ReservationPolicyChangeService reservationPolicyChangeService;

    @PutMapping
    public ResponseSuccessNoResult changeReservationPolicy(@RequestBody ReservationPolicyChangeRequest reservationPolicyChangeRequest) {
        reservationPolicyChangeService.changeReservationPolicy(reservationPolicyChangeRequest);

        return new ResponseSuccessNoResult(RESERVATION_POLICY_CHANGE_FAIL);
    }
}
