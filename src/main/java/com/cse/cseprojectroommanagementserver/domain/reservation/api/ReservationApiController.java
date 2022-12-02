package com.cse.cseprojectroommanagementserver.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationAuthService;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationStatusChangeService;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReserveTableService;
//import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchDto;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationSearchService;
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
    private final ReservationSearchService reservationSearchService;
    private final ReservationStatusChangeService reservationStatusChangeService;
    private final ReservationAuthService reservationAuthService;
    private final AuthService authService;

    //일반 웹 예약
    @PostMapping
    public ResponseSuccessNoResult reserveByWeb(@RequestBody ReserveRequest reserveRequest) {
        reserveTableService.reserve(reserveRequest);
        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

    //현장 예약
    @PostMapping("/onsite/qr")
    public ResponseSuccessNoResult reserveOnSiteByQRLogin(@RequestBody OnsiteReservationRequestByQR reservationRequest) {
        Member matchedMember = authService.getMatchedMember(reservationRequest.getAccountQRContents());
        reserveTableService.reserveOnsiteByAccountQR(matchedMember, reservationRequest);
        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

    @PostMapping("/onsite/form")
    public ResponseSuccessNoResult reserveOnSiteByFromLogin(@RequestBody OnsiteReservationRequestByLoginForm reservationRequest) {
        Member matchedMember = authService.getMatchedMember(reservationRequest.getLoginId(), reservationRequest.getPassword());
        reserveTableService.reserveOnsiteByFormLogin(matchedMember, reservationRequest);

        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

    @GetMapping("/projectrooms/{id}")
    public ResponseSuccess<List<SearchReservationResponse>> getReservationListByProjectRoom(@PathVariable("id") Long projectRoomId,
                                                                                         @ModelAttribute FirstAndLastDateTimeRequest firstAndLastDateTimeRequest) {
        List<SearchReservationResponse> searchReservationList = reservationSearchService.searchReservationListByProjectRoom(projectRoomId, firstAndLastDateTimeRequest);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, searchReservationList);
    }

    @GetMapping("/tables")
    public ResponseSuccessNoResult isCurrentReservedTable(@RequestParam String tableName) {
        reservationSearchService.checkIsInUseTableAtCurrent(tableName);
        return new ResponseSuccessNoResult(IN_USE_TABLE);
    }

    @GetMapping("/current/members/{id}")
    public ResponseSuccess<List<CurrentReservationByMemberResponse>> getCurrentReservationListOfMember(@PathVariable("id") Long memberId) {
        List<CurrentReservationByMemberResponse> myCurrentReservationList = reservationSearchService.searchMyCurrentReservationList(memberId);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, myCurrentReservationList);
    }

    @GetMapping("/past/members/{id}")
    public ResponseSuccess<List<PastReservationByMemberResponse>> getPastReservationListOfMember(@PathVariable("id") Long memberId) {
        List<PastReservationByMemberResponse> myPastReservationList = reservationSearchService.searchMyPastReservationList(memberId);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, myPastReservationList);
    }

    @PatchMapping("/{id}")
    public ResponseSuccessNoResult cancelReservation(@PathVariable("id") Long reservationId) {
        reservationStatusChangeService.cancelReservation(reservationId);
        return new ResponseSuccessNoResult(RESERVATION_CANCEL_SUCCESS);
    }

    @PatchMapping("/auth/qr")
    public ResponseSuccessNoResult authReservationQR(@RequestBody QRAuthRequest qrContents) {
        reservationAuthService.qrReservationAuth(qrContents);
        return new ResponseSuccessNoResult(RESERVATION_QR_AUTH_SUCCESS);
    }





}