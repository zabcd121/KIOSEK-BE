package com.cse.cseprojectroommanagementserver.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReserveTableService;
//import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchDto;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.SearchReservationService;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationRequestDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReserveTableService reserveTableService;
    private final SearchReservationService searchReservationService;

    //일반 웹 예약
    @PostMapping
    public ResponseSuccessNoResult reserveByWeb(@RequestBody ReserveRequest reserveRequest) {
        reserveTableService.reserve(reserveRequest);
        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

    //현장 예약
    @PostMapping("/onsite")
    public ResponseSuccessNoResult reserveOnSite(@RequestBody ReserveRequest reserveRequest) {
        reserveTableService.reserveOnsite(reserveRequest);
        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

    @GetMapping("/projectroom/{id}")
    public ResponseSuccess<List<SearchReservationResponse>> getReservationListByProjectRoom(@PathVariable("id") Long projectRoomId,
                                                                                         @RequestParam FirstAndLastDateTimeRequest firstAndLastDateTimeRequest) {
        List<SearchReservationResponse> searchReservationList = searchReservationService.searchReservationListByProjectRoom(projectRoomId, firstAndLastDateTimeRequest);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, searchReservationList);
    }

    @GetMapping("/current/members/{id}")
    public ResponseSuccess<List<CurrentReservationByMemberResponse>> getMemberCurrentReservationList(@PathVariable("id") Long memberId) {
        List<CurrentReservationByMemberResponse> currentReservationList = searchReservationService.searchCurrentReservationListByMember(memberId);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, currentReservationList);
    }

    @PatchMapping("/{id}")
    public void cancelReservation(@PathVariable("id") Long reservationId) {

    }



}