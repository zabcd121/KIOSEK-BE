package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyReqDto;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.RESERVATION_POLICY_CHANGE_SUCCESS;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminReservationPolicyApiController {

    private final ReservationPolicyChangeService reservationPolicyChangeService;

    @PutMapping("/v1/reservations/policies")
    public ResponseSuccessNoResult changeReservationPolicy(@RequestBody ReservationPolicyReqDto.ReservationPolicyChangeReq reservationPolicyChangeReq) {
        reservationPolicyChangeService.changeReservationPolicy(reservationPolicyChangeReq);

        return new ResponseSuccessNoResult(RESERVATION_POLICY_CHANGE_SUCCESS);
    }
}
