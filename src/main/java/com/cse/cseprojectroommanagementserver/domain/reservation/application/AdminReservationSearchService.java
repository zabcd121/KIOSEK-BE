package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.domain.repository.ReservationSearchableRepository;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminReservationSearchService {

    private final ReservationSearchableRepository reservationSearchableRepository;

    public Page searchReservationLogList(ReservationSearchCondition condition, Pageable pageable) {
        return reservationSearchableRepository.findAllByConditionAndPageable(condition, pageable);
    }
}
