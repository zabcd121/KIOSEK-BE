package com.cse.cseprojectroommanagementserver.domain.reservation.api;

import com.cse.cseprojectroommanagementserver.domain.reservation.application.AdminReservationSearchService;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode;
import com.cse.cseprojectroommanagementserver.global.common.dto.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cse.cseprojectroommanagementserver.global.common.ResponseConditionCode.*;

@RestController
@RequestMapping("/api/admins/reservations")
@RequiredArgsConstructor
public class AdminReservationApiController {

    private final AdminReservationSearchService adminReservationSearchService;

    @GetMapping
    public ResponseSuccess<Page> getReservationList(ReservationSearchCondition searchCondition, Pageable pageable) {
        return new ResponseSuccess(RESERVATION_SEARCH_SUCCESS, adminReservationSearchService.searchReservationLogList(searchCondition, pageable));
    }
}
