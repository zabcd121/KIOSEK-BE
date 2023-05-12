package com.cse.cseprojectroommanagementserver.domain.reservationpolicy.api;

import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicyChangeService;
import com.cse.cseprojectroommanagementserver.domain.reservationpolicy.application.ReservationPolicySearchService;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponse;
import com.cse.cseprojectroommanagementserver.global.success.SuccessResponseNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservationpolicy.dto.ReservationPolicyResDto.*;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.RESERVATION_POLICY_CHANGE_SUCCESS;
import static com.cse.cseprojectroommanagementserver.global.success.SuccessCode.RESERVATION_POLICY_SEARCH_SUCCESS;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminReservationPolicyApiController {

    private final ReservationPolicyChangeService reservationPolicyChangeService;
    private final ReservationPolicySearchService reservationPolicySearchService;

    @PutMapping("/v1/reservations/policies")
    public SuccessResponseNoResult changeReservationPolicy(@RequestBody @Validated ReservationPolicyChangeReq reservationPolicyChangeReq) {
        reservationPolicyChangeService.changeReservationPolicy(reservationPolicyChangeReq);

        return new SuccessResponseNoResult(RESERVATION_POLICY_CHANGE_SUCCESS);
    }

    @GetMapping("/v1/reservations/policies")
    public SuccessResponse<ReservationPolicySearchRes> getCurrentReservationPolicy() {
        return new SuccessResponse(RESERVATION_POLICY_SEARCH_SUCCESS, reservationPolicySearchService.searchReservationPolicy());
    }
}
