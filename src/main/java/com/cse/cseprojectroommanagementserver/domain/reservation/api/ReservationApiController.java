package com.cse.cseprojectroommanagementserver.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.member.application.AuthService;
import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.reservation.application.*;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccessNoResult;
import com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResDto.*;
import static com.cse.cseprojectroommanagementserver.global.common.ResConditionCode.*;
import static com.cse.cseprojectroommanagementserver.global.jwt.JwtTokenProvider.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReserveTableService reserveTableService;
    private final ReserveTableFacadeService reserveTableFacadeService;
    private final ReservationSearchService reservationSearchService;
    private final ReservationCancelService reservationCancelService;
    private final ReservationAuthService reservationAuthService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    //일반 웹 예약
    @PostMapping("/v2/reservations")
    public ResponseSuccessNoResult reserveByWeb(@RequestBody @Validated ReserveReq reserveReq, HttpServletRequest request) {
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER))));
        reserveTableFacadeService.reserve(memberId, reserveReq);
        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

    //현장 예약
    @PostMapping("/v1/reservations/onsite/qr")
    public ResponseSuccessNoResult reserveOnSiteByQRAuth(@RequestBody @Validated OnsiteReservationByQRReq reservationReq) {
        Member matchedMember = authService.searchMatchedMember(reservationReq.getAccountQRContents());
        reserveTableService.reserveOnsiteByAccountQR(matchedMember, reservationReq);
        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
    }

//    @PostMapping("/v1/reservations/onsite/form")
//    public ResponseSuccessNoResult reserveOnSiteByFromLogin(@RequestBody OnsiteReservationByLoginFormReq reservationReq) {
//        Member matchedMember = authService.searchMatchedMember(reservationReq.getLoginId(), reservationReq.getPassword());
//        reserveTableService.reserveOnsiteByFormLogin(matchedMember, reservationReq);
//
//        return new ResponseSuccessNoResult(RESERVATION_SUCCESS);
//    }

    @GetMapping("/v1/reservations")
    public ResponseSuccess<ReservationImpossibleSearchRes> getReservationListByProjectRoom(@RequestParam Long projectRoomId,
                                                                                       @ModelAttribute FirstAndLastDateTimeReq firstAndLastDateTimeReq) {
        ReservationImpossibleSearchRes reservationImpossibleSearchRes = reservationSearchService.searchReservationListByProjectRoom(projectRoomId, firstAndLastDateTimeReq);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, reservationImpossibleSearchRes);
    }

    /**
     * Q&A 스피커에서 사람이 감지되었을 때 현재 이 테이블이 예약되어 사용중인 테이블인지 검사하는 API
     */
    @GetMapping("/sensor/v1/reservations/check")
    public ResponseSuccessNoResult validateIsCurrentReservedTable(@RequestParam String tableName) {
        reservationSearchService.checkIsInUseTable(tableName);
        return new ResponseSuccessNoResult(IN_USE_TABLE);
    }

    @GetMapping("/v2/reservations/current")
    public ResponseSuccess<List<CurrentReservationByMemberRes>> getCurrentReservationListOfMember(HttpServletRequest request) {
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER))));
        List<CurrentReservationByMemberRes> myCurrentReservationList = reservationSearchService.searchMyCurrentReservationList(memberId);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, myCurrentReservationList);
    }

    @GetMapping("/v2/reservations/past")
    public ResponseSuccess<List<PastReservationByMemberRes>> getPastReservationListOfMember(HttpServletRequest request) {
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER))));
        List<PastReservationByMemberRes> myPastReservationList = reservationSearchService.searchMyPastReservationList(memberId);
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, myPastReservationList);
    }

    @DeleteMapping("/v1/reservations/{id}")
    public ResponseSuccessNoResult cancelReservationByMember(@PathVariable("id") Long reservationId, HttpServletRequest request) {
        Long memberId = Long.parseLong(jwtTokenProvider.getSubject(jwtTokenProvider.resolveToken(request.getHeader(AUTHORIZATION_HEADER))));
        reservationCancelService.cancelReservation(memberId, reservationId);
        return new ResponseSuccessNoResult(RESERVATION_CANCEL_SUCCESS);
    }

    @PostMapping("/v1/reservations/auth")
    public ResponseSuccessNoResult checkInWithReservationQR(@RequestBody @Validated QRAuthReq qrContent) {
        reservationAuthService.checkInWIthReservationQR(qrContent);
        return new ResponseSuccessNoResult(RESERVATION_QR_CHECKIN_SUCCESS);
    }
}