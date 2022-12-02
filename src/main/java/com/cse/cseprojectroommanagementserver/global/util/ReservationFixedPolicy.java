package com.cse.cseprojectroommanagementserver.global.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ReservationFixedPolicy {
    POSSIBLE_CHECKIN_TIME_BEFORE(10),
    POSSIBLE_CHECKIN_TIME_AFTER(20);

    private final int value;
}
