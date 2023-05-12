package com.cse.cseprojectroommanagementserver.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.reservation.application.ReservationSearchService;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResDto.*;
import static com.cse.cseprojectroommanagementserver.global.dto.SuccessCode.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminReservationApiController {

    private final ReservationSearchService reservationSearchService;

    @GetMapping("/v1/reservations")
    public SuccessResponse<Page<SearchReservationByPagingRes>> getReservationList(ReservationSearchCondition searchCondition, Pageable pageable) {
        return new SuccessResponse(RESERVATION_SEARCH_SUCCESS, reservationSearchService.searchReservationListByConditionAndPageable(searchCondition, pageable));
    }
}
