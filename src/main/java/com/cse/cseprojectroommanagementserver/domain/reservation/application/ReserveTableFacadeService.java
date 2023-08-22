package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.repository.NamedLockReservationRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.RESERVATION_COUNT;

@Service
@RequiredArgsConstructor
public class ReserveTableFacadeService {

    private final NamedLockReservationRepository namedLockReservationRepository;
    private final ReserveTableService reserveTableService;
    private final RedisTemplate redisTemplate;

    @Timed("kiosek.reservation")
    @Transactional
    public void reserve(Long memberId, ReserveReq reserveReq) {
        String key = reserveReq.getStartAt().toLocalDate().toString() + reserveReq.getProjectTableId();
        try {
            namedLockReservationRepository.getLock(key);
            reserveTableService.reserve(memberId, reserveReq);
        } finally {
            namedLockReservationRepository.releaseLock(key);
            redisTemplate.delete(RESERVATION_COUNT + "*");
        }
    }
}