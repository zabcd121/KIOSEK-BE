package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.repository.NamedLockReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationRequestDto.*;

@Service
@RequiredArgsConstructor
public class ReserveTableFacadeService {

    private final NamedLockReservationRepository namedLockReservationRepository;
    private final ReserveTableService reserveTableService;

    @Transactional
    public void reserve(ReserveRequest reserveRequest) {
        String key = reserveRequest.getStartAt().toLocalDate().toString() + reserveRequest.getProjectTableId();
        try {
            namedLockReservationRepository.getLock(key);
            reserveTableService.reserve(reserveRequest);
        } finally {
            namedLockReservationRepository.releaseLock(key);
        }
    }
}