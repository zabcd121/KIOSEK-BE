package com.cse.cseprojectroommanagementserver.domain.visitor.application;

import com.cse.cseprojectroommanagementserver.domain.visitor.domain.model.Visitor;
import com.cse.cseprojectroommanagementserver.domain.visitor.domain.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitorScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final VisitorRepository visitorRepository;

    @Scheduled(initialDelay = 3000000, fixedDelay = 3000000)
    @Transactional
    public void updateVisitorData() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!visitorRepository.existsByUserIpAndDate(userIp, date)){
                Visitor visitor = Visitor.builder()
                        .userAgent(userAgent)
                        .userIp(userIp)
                        .date(date)
                        .build();

                visitorRepository.save(visitor);
            }

            redisTemplate.delete(key);
        }
    }

}
