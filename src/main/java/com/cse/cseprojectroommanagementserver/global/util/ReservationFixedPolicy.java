package com.cse.cseprojectroommanagementserver.global.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ReservationFixedPolicy {
    POSSIBLE_CHECKIN_TIME_BEFORE(20),//2023-05-08 15:00:00 배포에 변경함 (10분 -> 20분)
    POSSIBLE_CHECKIN_TIME_AFTER(20);

    private final int value;
}
