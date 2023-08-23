package com.cse.cseprojectroommanagementserver.domain.reservation.application;

import com.cse.cseprojectroommanagementserver.domain.reservation.repository.NamedLockRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationReqDto.*;
import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.RESERVATION_COUNT;

@Service
@RequiredArgsConstructor
public class ReserveTableFacadeService {

    private static final int LOCK_TIMEOUT_SECONDS = 3;

    private final NamedLockRepository namedLockRepository;
    private final ReserveTableService reserveTableService;
    private final RedisTemplate redisTemplate;

    @Timed("kiosek.reservation")
    public void reserve(Long memberId, ReserveReq reserveReq) {
        String lockName = reserveReq.getStartAt().toLocalDate().toString() + reserveReq.getProjectTableId();
        namedLockRepository.executeWithLock(lockName, LOCK_TIMEOUT_SECONDS,
                () -> reserveTableService.reserve(memberId, reserveReq));

        redisTemplate.delete(RESERVATION_COUNT + "*");
    }
}